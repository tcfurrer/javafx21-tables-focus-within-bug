## Minimal Reproducible Example for a JavaFX Tables focus-within bug

The focusWithin property is not always set correctly for tables.

Instructions to reproduce:
1) Launch the given demo App
2) Notice that initial focus is in the TextField. 
   * Broken: The TableView's focusWithin is true.
3) Click on any cell in the TableView to focus it and set it's selection
4) Click on the TextField to focus it. 
   * Broken: The TableView's focusWithin is true.
5) Press ESCAPE to clear the TableView's selection.
   Now the TableView focusWithin is false. Although that is correct,
   it is not expected/intuitive for clearing the TableView's selection 
   to have the side effect of changing it's focusWithin.

