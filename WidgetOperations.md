

# Creating #
Widgets are created by instantiating the respective class. For example, a [PdButton ](http://purewidgets.googlecode.com/git/doc-public/org/purewidgets/client/widgets/PdButton.html) widget is created by:

```
// Create a button widget giving it an id and a label
 PdButton pdButton = new PdButton("myButtonId", "Activate me"); 
```

The first argument is the widget id, and it must be unique among your application's widgets. The widget id is what allows the Interaction Manager to distinguish different widgets. The widget id argument is common to all widgets. The rest of the arguments depend on the specific kind of widget.

Instantiating a widget, causes the PuReWidgets library to automatically send the widget to the Interaction Manager, making it available for interaction.

Of course, most often you will want to receive an event when someone interacts with your widget:
```
 // Register the action listener for the list 
 pdButton.addActionListener(new ActionListener() {
     @Override
     public void onAction(ActionEvent<?> e) {
        
         // Get the widget that triggered the event.             
         PdWidget source = (PdWidget) e.getSource();

         // If the button was activated, do something...
         if ( source.getWidgetId().equals("myButtonId") ) {
             // do something
         }
     }
 });
```

And you will most likely also want to display the widget in your application's graphical interface:
```
 // Add the graphical representation of the button to the browser
 // window.
 RootPanel.get().add(pdButton);
```


# Deleting #
For most applications, there is no need to delete widgets: their interactive features don't change. However, in some cases, it is necessary to delete a widget because that feature no longer exists.

To delete a widget you need to explicitly tell the PuReWidgets library that you want to do so:
```
// Create a button widget
PdButton pdButton = new PdButton("myButtonId", "Activate me"); 

// ...

// Delete that button
pdButton.removeFromServer();
```

Please be aware that the removeFromServer() call removes your widget from the PuReWidgets library internal data structures and from the InteractionManager. However, it does not remove your widget from the HTML DOM. To remove the widget from the DOM you need to use GWT's API, for example:
```
pdButton.removeFromParent();
```

or (if container is a panel that contains your widget):

```
container.clear()
```


# Associating icons with widgets #
In the web interface widgets can have optional icons associated with them. Icons can be associated with each WidgetOption. For most widgets, which have a single WidgetOption associating an icon can be done with
```
myButton.getWidgetOptions().get(0).setIconUrl("http://icon.url");
```

# Extending #
Soon...

# Styling #
Soon...