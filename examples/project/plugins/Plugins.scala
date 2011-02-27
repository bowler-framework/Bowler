import sbt._

class Plugins(info: ProjectInfo) extends PluginDefinition(info) {
  val templemoreRepo = "templemore sbt repo" at "http://templemore.co.uk/repo"
  val cucumberPlugin = "templemore" % "cucumber-sbt-plugin" % "0.5.0"


  //val bryanjswift = "Bryan J Swift" at "http://repos.bryanjswift.com/maven2/"
	// sbt-plugins
  //val plugins = "bryanjswift" % "sbt-plugins" % "0.3"
}