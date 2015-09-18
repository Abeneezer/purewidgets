

# Changing the input feedback on the public display #
PuReWidgets automatically shows an information popup panel on the public display with input feedback information, when users interact with any widget of your application. You can control what information is displayed and how the popup is styled.

The input feedback is slightly different depending on whether the widget that is receiving the input is currently on-screen (i.e., is visible on the public display), or off-screen (i.e., not visible on the public display).

## On-screen widgets ##
For on-screen widgets, PuReWidgets displays a popup panel over the widget with a single line of text. The text that is displayed can be configured using the [setOnScreenFeedbackInfo() method](http://purewidgets.googlecode.com/git/doc-public/org/purewidgets/client/widgets/PdWidget.html#setOnScreenFeedbackInfo(java.lang.String)):
```
pdButton.setOnScreenFeedbackInfo("A user has clicked me");
```

However, static messages are not very useful. Most often you want to display information that relates to the input itself. For this you can use special patterns that refer to various input variables. These patterns are coded in the [MessagePattern class](http://purewidgets.googlecode.com/git/doc-public/org/purewidgets/client/feedback/MessagePattern.html). For example if you want to display a message with the user's nickname:

```
pdButton.setOnScreenFeedbackInfo("User "+ MessagePattern.PATTERN_USER_NICKNAME +" has clicked me");
```

## Off-screen widgets ##
Off-screen widgets are widgets which have either not been added to the HTML DOM, or which are otherwise not visible on the display. Input feedback for off-screen widgets uses a popup panel at the bottom of the display (by default). The information on this popup can be set using two functions:
[setOffScreenFeedbackTitle()](http://purewidgets.googlecode.com/git/doc-public/org/purewidgets/client/widgets/PdWidget.html#setOffScreenFeedbackTitle(java.lang.String)) and [setOffScreenFeedbackInfo()](http://purewidgets.googlecode.com/git/doc-public/org/purewidgets/client/widgets/PdWidget.html#setOffScreenFeedbackInfo(java.lang.String))

By default, the title of the panel is set to the user's nickname:
```
pdButton.setOffScreenFeedbackTitle( MessagePattern.PATTERN_USER_NICKNAME );
```


By default, the info of the panel is set to the widget's short description:
```
pdButton.setOffScreenFeedbackTitle( MessagePattern.PATTERN_WIDGET_SHORT_DESCRIPTION );
```


## Styling ##

Soon...