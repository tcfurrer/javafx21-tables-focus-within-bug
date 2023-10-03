package bugdemo;

import javafx.application.Application;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.List;

import static javafx.beans.binding.Bindings.createStringBinding;
import static javafx.collections.FXCollections.observableArrayList;
import static javafx.collections.FXCollections.observableList;
import static javafx.scene.input.KeyEvent.KEY_PRESSED;

public final class App extends Application
{
    final Label label = new Label("Press ESC to clear TableView's selection");
    final TextField textField = new TextField();
    final Pane tableViewContainer = new VBox();
    final VBox windowContents = new VBox(
            label,
            textField,
            tableViewContainer
    );

    public static void main(String[] args)
    {
        launch();
    }

    @Override
    public void start(Stage stage)
    {
        stage.setTitle("Tables focus-within bug");

        textField.setPromptText("Dummy TextField");

        buildNewTable();

        final Scene scene = new Scene(new StackPane(windowContents), 400, 300);
        stage.setScene(scene);
        stage.show();
    }

    void buildNewTable()
    {
        var data = observableList(List.of("Item 1", "Item 2", "Item 3"));

        var column = new TableColumn<String,String>("Items");
        column.setPrefWidth(150);
        column.setCellValueFactory((TableColumn.CellDataFeatures<String, String> p) ->
                new ReadOnlyStringWrapper(p.getValue())
        );

        var tableView = new TableView<>(data);
        tableView.getColumns().add(column);

        windowContents.setStyle("-fx-padding: 20; -fx-spacing: 20; -fx-alignment: center;");
        windowContents.addEventFilter(KEY_PRESSED, e -> {
            if (e.getCode() == KeyCode.ESCAPE)
            {
                e.consume();
                tableView.getSelectionModel().clearSelection();
            }
        });

        var label = new Label("TableView's isFocusWithin = ");
        var state = new Label();
        state.textProperty().bind(createStringBinding(
                () -> tableView.isFocusWithin() ? "true" : "false",
                tableView.focusWithinProperty()
        ));
        var focusWithinIndicator = new HBox(label, state);

        tableViewContainer.getChildren().setAll(List.of(
                focusWithinIndicator,
                tableView
        ));
    }
}