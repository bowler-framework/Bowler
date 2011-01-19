# Bowler - A RESTful, Multi-Channel ready Scala Web Framework with a functional flavor
A RESTful, multi-channel ready web framework in Scala with a functional flavour, built on top of [Scalatra](http://www.scalatra.org/) and [Scalate](http://scalate.fusesource.org/), with [Lift-JSON](https://github.com/lift/lift/tree/master/framework/lift-base/lift-json/) doing the heavy JSON lifting.
The project is built with [Simple Build Tool ("sbt")](http://code.google.com/p/simple-build-tool/)- we intend to further add custom sbt processors in the future to automate boilerplate tasks.

More documentation and setup details are available on the [project website](http://bowlerframework.org)

## Design Goals (some of them)
* RESTfulness - being true to RESTful principles and the intent of the HTTP protocol.
* Multi-Channel ready - the number of clients is increasing: desktop browsers, smartphones like Android & iPhone, tablets like the iPad, and API's for third parties. Bowler aims to ease targeting multiple platforms at once by maximizing re-use and minimising re-work while allowing Layouts and Templates to be selected based on User-Agent, domain and other criteria.
* JSON API "for free" - to ease creating dynamic websites with JQuery, or providing partners an API, the default settings give you a JSON API "for free", with JSON rendered to the client based on the View Model you've set and the HTTP "Accept" header being set to "application/json" from the client.
* Decomposition of the View into "Resource View" & Layout - A consequence and enabler of the above two points, a stricter separation between the Resource View representing the Model you want to display and the Layout you want to surround it with. The idea behind this is explained here: [Decomposing the View in MVC - What's In a View?](http://blog.recursivity.com/post/2615673341/decomposing-the-view-in-mvc-whats-in-a-view)
* Sensible Defaults - Good to go out-of-the-box with sensible defaults and further extensions/plug-ins in the works for adding functionality.
* Composability - if you don't like the defaults, pick, choose and combine what you want. Bowler uses a "Micro Architecture" approach with strict code adherence to the [Single Responsibility Principle](http://en.wikipedia.org/wiki/Single_responsibility_principle). Adding for instance a different validation framework or different templating mechanism or even entirely different rendering mechanism should be relatively pain free.

## Building Instructions
* Have [sbt](http://code.google.com/p/simple-build-tool/) 0.7.5RC0 or later installed
* Pull and build the [Recursivity Commons project](https://github.com/wfaler/recursivity-commons), then do a "publish-local" on that project, as Bowler depends on this (we will make everything available in a Maven repo soon enough!)
* Go into the "core" folder, run sbt and the "update" command in sbt to download all dependencies
* Place the scala-compiler.jar in the "lib"-folder, this is a dependency of Scalate, and doesn't get automatically downloaded due to a bug in sbt.
* Now you're ready to build and go!

