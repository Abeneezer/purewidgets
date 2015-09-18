

# Testing your application #

A PuReWidgets application is essentially a GWT application so you test it in the same way: from Eclipse, choose Run as Web Application, and then open the local URL displayed in the Development Mode console in Google Chrome:

![https://googledrive.com/host/0B0wLiKlm4gz1Wk9xWHVVd3ExQVk?nonsense.png](https://googledrive.com/host/0B0wLiKlm4gz1Wk9xWHVVd3ExQVk?nonsense.png)



Chrome will complain about the GWT developer plugin the first time. You have to click to install the plugin and then reload the test web page:

![https://googledrive.com/host/0B0wLiKlm4gz1dlVseHkzQzdhTFU?nonsense.png](https://googledrive.com/host/0B0wLiKlm4gz1dlVseHkzQzdhTFU?nonsense.png)

In order to facilitate testing, you should run your applications in a custom place by adding "&placeid=`<yourname>`" to the URL. This way it is easier to identify your application in the web interaction page.

## Testing interactions ##
By default, PuReWidgets uses a test IM at http://pw-interactionmanager-test.appspot.com. The applications that use the test IM can be interacted with via Web by accessing the web interaction interface at [http://ecra.pt/test/&lt;yourname&gt;](http://ecra.pt/test/yourname). (By default, applications run on the DefaultPlace place, so if you don't change the test URL to include "&placeid=`<yourname>`", the web interaction web page will be at [http://ecra.pt/test/DefaultPlace](http://ecra.pt/test/DefaultPlace).

Currently, there is no setup needed on the IM, so you can just run your app and access the web interaction interface to interact with it. (This will change in the near future to require authentication...) Use the application id you define in the load call to identify your application in the test server.

These test servers are reset periodically so don't rely on them for lengthy tests...


When testing interaction from the web development mode, the PuReWidgets library will not be able to open a channel to the IM. This means that input may take a few seconds to arrive (the library will have to poll the IM). When deployed, there is no such delay.