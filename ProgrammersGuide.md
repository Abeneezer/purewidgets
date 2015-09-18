# Details #

When a widget is instantiated, it will automatically be sent to the Interaction Manager (IM) server (you generally don't have to worry about this, it is done by the toolkit). The data that is sent describes the widget (its options, labels, etc.) and allows the IM to keep track of every interactive feature in use by every instance of every application in every place. The widget is not sent immediately to the IM when the constructor finishes, there is a delay that allows you to call additional methods on the widget to set possible further configuration parameters. (This way, the toolkit does not have to keep checking if a widget's data has changed in order to update the IM. However, if you alter the widget afterwards, you will have to manually instruct the toolkit to update it on the IM...)

## Why do I need to set an Id for widgets ##
Because your application may be loaded and unloaded several times in the same public display in the course of its lifetime. The id allows the IM to know that a widget it is receiving is the same that is already has, so it doesn't duplicate entries.

## Sound ##
The input feedback also triggers a sound to provide an additional alert to users. The sound that is played is configurable through the "feedbacksound" application parameter (and can be disabled by pointing the parameter to an invalid or empty sound file).

# Contents #


# Setting up #
PuReWidgets is a standard GWT (and Appengine) library. Developing a PuReWidgets based application is similar to developing a GWT application.

What you need to create a PuReWidgets application:

  * [Eclipse](http://www.eclipse.org/) (or other GWT development tool)
  * [GWT](https://developers.google.com/web-toolkit/gettingstarted)
  * [the latest version of the PuReWidgets library](https://code.google.com/p/purewidgets/downloads/list)

## Configuring your GWT project ##
PuReWidgets depends on a number of GWT modules, some of them are external libraries you have to download and add to your build path.

  * [GWT GAE Channel](http://code.google.com/p/gwt-gae-channel/) for using a channel with the IM
  * [GWT Voices](http://code.google.com/p/gwt-voices/) for playing user input feedback sound

To use the PuReWidgets library, you need to add the inherit declaration in your .gwt.xml file:
```

<inherits name="org.purewidgets.PuReWidgets"/>
```

PuReWidgets relies on the internationalization module, and you may have to explicitly add the inherit declaration in you .gwt.xml file (PuReWidgets inherits this module, but for some reason I've had error if I don't do it again in the application):

```

<inherits name="com.google.gwt.i18n.I18N"/>
```

# Hello World #

```java

public class HelloWorld implements PDApplicationLifeCycle, EntryPoint {

@Override
public void onModuleLoad() {

// Give a name to the application and initialize some
// background processes.
PDApplication.load(this, "MyHelloWorld");
}

@Override
public void onPDApplicationLoaded(PDApplication pdApplication) {

// Create a button widget giving it an id and a label
PdButton pdButton = new PdButton("myButtonId", "Activate me");


// Register the action listener for the list
pdButton.addActionListener(new ActionListener() {
@Override
public void onAction(ActionEvent<?> e) {

// Get the widget that triggered the event.
PdWidget source = (PdWidget) e.getSource();

// If the button was activated, do something...
if ( source.getWidgetId().equals("myButtonId") ) {
RootPanel.get().add(new Label("Button activated"));
}
}
});


// Add the graphical representation of the button to the browser
// window.
RootPanel.get("main").add(pdButton);
}
}```

Since a PuReWidgets application is also a GWT application, it's entry point is GWT's onModuleLoad() method. In the entry point, we simply load our application, giving it a default instance id (this can be replaced by the place owner, so don't rely on it).

After the application is loaded, it will call the onPDApplicationLoaded(), where we can build on the functionalities of PuReWidgets.

In this HelloWorld example, we simply create an action button widget. All widgets must have an id given by the programmer, unique among the application's widgets. The button also has a label, which is displayed graphically on the public display.

In PuReWidgets, all widgets trigger an ActionEvent that describes the high-level event generated by the widget in response to user input. For the button, we simply need to check which button (in this example, we didn't even need to check this because there is only one, but in general you will have to) has triggered the event, and act accordingly.

The last thing we do in the HelloWorld example is to add the graphical representation of the button widget to the document, so that it will appear on the public display. This is not a required step, as PuReWidgets' widgets are actionable even if they are not visible.

# Testing your application #
By default, PuReWidgets uses a test IM at http://pw-interactionmanager-test.appspot.com. The applications that use the test IM can be interacted with via Web by accessing the SystemApps server at http://pw-systemapps-test.appspot.com. By default, applications run on the DefaultPlace place.

Currently, there is no setup needed on the IM, so you can just run your app and access the SystemApps to interact with it. (This will change in the near future to require authentication...) Use the application id you define in the load call to identify your application in the test server.

These test servers are reset periodically so don't rely on them for lengthy tests...

# Details #

When a widget is instantiated, it will automatically be sent to the Interaction Manager (IM) server (you generally don't have to worry about this, it is done by the toolkit). The data that is sent describes the widget (its options, labels, etc.) and allows the IM to keep track of every interactive feature in use by every instance of every application in every place. The widget is not sent immediately to the IM when the constructor finishes, there is a delay that allows you to call additional methods on the widget to set possible further configuration parameters. (This way, the toolkit does not have to keep checking if a widget's data has changed in order to update the IM. However, if you alter the widget afterwards, you will have to manually instruct the toolkit to update it on the IM...)

## Why do I need to set an Id for widgets ##
Because your application may be loaded and unloaded several times in the same public display in the course of its lifetime. The id allows the IM to know that a widget it is receiving is the same that is already has, so it doesn't duplicate entries.