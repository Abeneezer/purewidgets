

# Introduction #
<img width='300px' src='https://googledrive.com/host/0B0wLiKlm4gz1anZVT3RkQ1VCSGs?nonsense.png'>

A public display (PD) application exists in an environment which is very different from a desktop application.<br>
<br>
First of all, the goal is that the application's content is <i>situated</i>, meaning that a PD application may run in displays in various places, but will typically generate different content for each place. There are different ways to provide this content adaptation:<br>
<ul><li>The application reacts to users' interactions (which will naturally be different from place to place),<br>
</li><li>The application provides an administration console which the display owner will use to configure and adapt the application to a particular place,<br>
</li><li>The application is smart enough to detect it's location and provide content accordingly.</li></ul>

Second, PD applications are multi-user, meaning that they may receive input events from several users at the same time. Your application will want to be able to differentiate between different users.<br>
<br>
Third, there may be various input mechanisms with which users can interact with a public display application (SMS, Bluetooth, email, QRcode, mobile app, etc.). However, you as a programmer, don't really want to deal with these low-level details. Instead you are interested in high-level interaction controls.<br>
<br>
Fourth, PD applications may not run continuously on the public display, but instead be scheduled to run at pre-determined moments in time. However, they should still receive input even if not running on the public display.<br>
<br>
PuReWidgets provides a number of features that take the burden out of programmers when dealing with applications in this new computing platform. In order to do this it makes use of several components and concepts, which are explained next. The following diagram shows the main software components that are at play:<br>
<br>
<img src='https://www.lucidchart.com/publicSegments/view/50c66ba7-1b9c-4828-81ed-4f990ad7dee0/image.png' />


<h1>Interaction Manager server</h1>
The Interaction Manager (IM) is the central system of the PuReWidgets toolkit. It's a server that mediates all user interaction with the various applications that may be associated with different places.<br>
The IM manages information about the Places, Applications, Widgets, WidgetOptions, ReferenceCodes, and user input.<br>
<br>
<img src='https://www.lucidchart.com/publicSegments/view/506ae761-3bd0-4a1b-a632-322e0a2edd9e/image.png' />

The IM allows applications to receive input even when they are not running. It keeps an input queue ready to be queried when an application is started on the public display.<br>
<br>
It also provides a centralized web page for interacting with the applications of a given place.<br>
<br>
<h2>PuReWidgets library</h2>
The IM provides a REST interface for other components of the toolkit (or outside of the toolkit).<br>
In general, programmers don't need to worry about the IM or interfacing with it, because the programmers interface with the PuReWidgets library which encapsulates all the communication that is necessary to take place with the IM.<br>
<br>
Also, application programmers just assume that the IM server exists in the display network where their applications will run. Installing the IM is the job of the place owner (or the display network administrator).<br>
<br>
<br>
<h1>Places</h1>
A Place is simply an administrative region, which can have different levels of granularity. A Place can be something small like a specific cafeteria, with a single public display, inside a university campus, or a wider place like the university campus itself, with various public displays.  An Interaction Manager server can handle multiple Places, and each Place is identified by a unique <i>place id</i>, within the IM.<br>
<br>
<h2>Place Owner</h2>
The place owner is the guy that owns or manages a place and all associated displays and applications.<br>
<br>
<h1>Applications</h1>
This is what you want to develop, right? A PuReWidgets application is simply a GWT/Appengine application that uses the PuReWidgets library and the Interaction Manager server to provide public display interaction.<br>
<br>
A public display will typically run various applications, and when you develop an application you don't really know where it will run.<br>
<br>
An application may be "associated" with various places (i.e., place owners may choose to display it in their PDs), and in a single place it may have various instances. When a place owner "associates" an application with a place he gives that association an instance name -- a unique <i>application id</i>, within that place. In the Interaction Manager server, an instance of an application is thus identified by the pair: place id + application id.<br>
<br>
(Although instances are independent entities in the Interaction Manager server, it is up to the application developer to choose to maintain separate or joint application data across the various instances. In the simplest case, various instances will share the same database and, hence, display the same content.)<br>
<br>
<br>
<h1>Widgets</h1>
Most of the time, programmers don't have to worry about place ids, or application ids: that is managed by the PuReWidgets library. Widgets, however, are the focus of the developer's work.<br>
<br>
A (PuReWidgets) widget represents an interactive feature of an application. PuReWidgets provides various types of widgets for getting different types of input from users:<br>
<br>
<ul><li><a href='http://purewidgets.googlecode.com/git/doc-public/index.html?org/purewidgets/client/widgets/PdButton.html'>Button</a> For providing actionable commands to users.<br>
</li><li><a href='http://purewidgets.googlecode.com/git/doc-public/index.html?org/purewidgets/client/widgets/PdTextBox.html'>TextBox</a> For getting text data from users.<br>
</li><li><a href='http://purewidgets.googlecode.com/git/doc-public/index.html?org/purewidgets/client/widgets/PdListBox.html'>ListBox</a> For allowing users to select among a set of options.<br>
</li><li><a href='http://purewidgets.googlecode.com/git/doc-public/index.html?org/purewidgets/client/widgets/PdDownload.html'>Download</a> For allowing users to download a file from your application.<br>
</li><li><a href='http://purewidgets.googlecode.com/git/doc-public/index.html?org/purewidgets/client/widgets/PdUpload.html'>Upload</a> For allowing users to upload a file to your application.<br>
</li><li><a href='http://purewidgets.googlecode.com/git/doc-public/index.html?org/purewidgets/client/widgets/PdCheckin.html'>Checkin</a> For receiving check-in events for the place where your application is running in.</li></ul>

<h2>Widget Ids</h2>
An application can create as many instances of a widget as it needs. Each instance must have a widget id, that is unique among the application's widget instances. The Interaction Manager server uses the widget id to associate a widget instance with a particular application running in a specific place. The triplet (place id + application id + widget id) uniquely identifies a widget instance within the Interaction Manager server.<br>
<br>
<h2>Widget Options</h2>
Widget options are independently actionable items within a widget. Most widgets have a single option, but, for example, a ListBox widget has various options that users can independently select. A widget has at least one widget option.<br>
<br>
<h3>Reference codes</h3>
The Interaction Manager assigns a textual reference code to each widget option. These reference codes are human-readable identifiers to be used in text-based interactions (such as SMS or email interactions), allowing users to address individual options within a widget. (Since most widgets have a single option, most reference codes are references to widget instances).<br>
<br>
The information about every widget is kept on the IM, allowing it to route user input information to the appropriate application.<br>
<br>
Programmers don't generally have to worry about widget options or reference codes as these are managed by the PuReWidgets library.<br>
<br>
<h1>Interaction</h1>
When your application runs on a display, users can interact with it using various interaction mechanisms.<br>
<br>
<br>
<h2>Web interaction</h2>
<img width='150px' src='https://googledrive.com/host/0B0wLiKlm4gz1MjhTTmRldlhKNXc?nonsense.png'>

PuReWidgets automatically creates a web interface that allows users to interact with the current applications in a place. The address of this interface is place dependant (See <a href='TestingApplication.md'>Testing your application</a> for how to use this interface for testing purposes).<br>
<br>
<h2>Text-based interaction</h2>
PuReWidgets supports text-based interactions which make use of the generated reference codes. Currently, users can interact via SMS or email.<br>
<br>
<h2>QR codes</h2>
PuReWidgets provides place owners with a web interface where they can download QR codes that address specific features of any application running in a specific place. (Currently applications cannot get these QR codes automatically.)<br>
<br>
<h2>Touch</h2>
Some widgets support touch interactions. If your application is run on a touch enabled display, users will be able to interact anonymously with some of its widgets (Buttons, Textboxes, Listboxes).<br>
<br>
<h1>Input Feedback</h1>
PuReWidgets provides a consistent model for user input feedback on the public display.<br>
<br>
<h2>On-screen widgets</h2>
When a widget that is currently visible on the public display (on-screen) receives input, it triggers an information popup panel over the graphical representation of the widget indicating to users that the input was received.<br>
<br>
<h2>Off-screen widgets</h2>
If a widget is not visible on the public display (off-screen) and receives input, it will trigger an information popup panel in the bottom of the screen. This popup includes more information than the one for on-screen widgets, to allow users to relate the input to the correct widget.