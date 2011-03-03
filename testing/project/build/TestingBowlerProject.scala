import sbt._

class TestingBowlerProject(info: ProjectInfo) extends DefaultWebProject(info){
  	val scalatest = "org.scalatest" % "scalatest" %  "1.2"

  val scalatraTest = "org.scalatra" %% "scalatra-scalatest" % "2.0.0.M2"

	val jetty6 = "org.mortbay.jetty" % "jetty" % "6.1.14"

  val servletApi = "javax.servlet" % "servlet-api" % "2.5"

  val selenium = "org.seleniumhq.selenium" % "selenium" % "2.0a4"

  val sonatypeNexusSnapshots = "Sonatype Nexus Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots"
  val sonatypeNexusReleases = "Sonatype Nexus Releases" at "https://oss.sonatype.org/content/repositories/releases"


}