# Bowler Example Application
This is the Bowler example application that show cases some of Bowlers basic functionality and integration with JPA and Squeryl.
Just use SBT as you would for any SBT project to run the example app, jetty-run from within sbt will start your webapp at http://localhost:8080

## What's this Ruby malarky in the project?!
To run automated browser based acceptance testing of the app (once it is running), we have opted for creating automated user acceptance tests in Ruby with Cucumber and Watir.
We could have used Scala or Java-bindings with Cuke4Duke and and Selenium2, but opted for Ruby in this case, as running JRuby from within SBT is painfully slow for now.
### Pre-requisites for the automated user acceptance tests
* Have Ruby installed
* Have RubyGems installed
* run the embedded jetty from sbt with "jetty-run"
* Install the following gems:

#### Required Ruby Gems
	sudo gem install rspec
	sudo gem install cucumber
	sudo gem install watir
	sudo gem install firewatir
	sudo gem install watir-webdriver
#### Running the suite
After this, simply run "cucumber" from the root of the project (but outside sbt). We will look to find ways of improving this going forward.
