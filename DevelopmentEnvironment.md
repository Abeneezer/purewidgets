

# Setting up Eclipse #
PuReWidgets is a standard GWT (and Appengine) library. Developing a PuReWidgets based application is similar to developing a GWT application.

What you need to create a PuReWidgets application:

  1. Download and install [Eclipse IDE for Java Developers](http://www.eclipse.org/) (or other GWT development tool)
  1. Download and install the [GWT Plugin for Eclipse (which includes the SDK)](https://developers.google.com/web-toolkit/download)

# Running the HelloWorld PuReWidgets project #
  1. Download the  [HelloWorld sample project](http://code.google.com/p/purewidgets/downloads/detail?name=HelloWorld.zip&can=2)
  1. Import the HelloWorld project into Eclipse
  1. Fix the java build path errors (right-click on the project and go to "Project Properties->Java Build Path" and fix the errors by locating the libraries (they are in the project's folder).
  1. Test the HelloWorld by right-clicking on the project and choosing "Run As->Web Application". You may need to add a "-startupUrl helloworld/HelloWorld.html" argument to the run configuration for it to be able to generate the right development URL.

# Creating a project from scratch #
  1. Create a standard GWT project
  1. [Download the latest version of the PuReWidgets library](https://code.google.com/p/purewidgets/downloads/list)
  1. [Download gwt-gae-channel-0.4-alpha.jar (pay attention to the version number) ](http://code.google.com/p/gwt-gae-channel/downloads/list)
  1. [Download objectify-3.0.1.jar](http://code.google.com/p/objectify-appengine/downloads/list)
  1. [Download gwt-voices-2.1.8.jar](http://code.google.com/p/gwt-voices/downloads/list)
  1. Add all the above jar files to the project build path.
  1. Copy PuReWidgets and Objectify library jar files to the war/WEB-INF/lib folder.
  1. Add the following inherits to your module.gwt.xml file:
```

  <inherits name="org.purewidgets.PuReWidgets"/>
  <inherits name="com.google.gwt.i18n.I18N"/>

```
  1. Add the following to your web.xml file:
```

  <servlet>
    <servlet-name>updateapplication</servlet-name>
    <servlet-class>org.purewidgets.server.cron.UpdateApplication</servlet-class>
  </servlet>
  
<servlet-mapping>
  <servlet-name>updateapplication</servlet-name>
  <url-pattern>/updateapplication</url-pattern>
</servlet-mapping>
  
<servlet>
  <servlet-name>HttpServlet</servlet-name>
  <servlet-class>org.purewidgets.server.http.HttpServiceImpl</servlet-class>
</servlet>
  
<servlet-mapping>
  <servlet-name>HttpServlet</servlet-name>
  <url-pattern>/httpservice</url-pattern>
</servlet-mapping>
  
<servlet>
  <servlet-name>remoteStorageServlet</servlet-name>
  <servlet-class>org.purewidgets.server.storage.ServerStorageServiceImpl</servlet-class>
</servlet>
  
<servlet-mapping>
  <servlet-name>remoteStorageServlet</servlet-name>
  <url-pattern>/storageservice</url-pattern>
</servlet-mapping>

```