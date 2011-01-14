import sbt._


class BowlerExamplesProject(info: ProjectInfo) extends DefaultWebProject(info){
	val bowler = "org.bowlerframework" % "bowler-core_2.8.1" % "0.1"
	
  val slf4jVersion = "1.6.0"
	
  val sfl4jnop = "org.slf4j" % "slf4j-nop" % slf4jVersion % "runtime"

  val jetty6 = "org.eclipse.jetty" % "jetty-server" % "7.2.0.v20101020" % "test"
	val jettyWebapp = "org.eclipse.jetty" % "jetty-webapp" % "7.2.0.v20101020" % "test"

  val servletApi = "javax.servlet" % "servlet-api" % "2.5" % "provided"

  val sonatypeNexusSnapshots = "Sonatype Nexus Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots" 
  val sonatypeNexusReleases = "Sonatype Nexus Releases" at "https://oss.sonatype.org/content/repositories/releases"

  val scalateRepo = "scalate repo" at "http://repo.fusesource.com/nexus/content/repositories/public/"

  val scalaToolsRepo = "Scala-Tools repo" at "http://scala-tools.org/repo-releases/"
}