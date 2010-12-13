import sbt._

class CoreBowlerProject(info: ProjectInfo) extends DefaultProject(info){
	
	//override def scanDirectories = Nil

  	val slf4jVersion = "1.6.0"
	//val hsqldb = "hsqldb" % "hsqldb" % "1.8.0.10" % "test"
	val scalatest = "org.scalatest" % "scalatest" %
	    "1.2" % "test"

  val scalatraTest = "org.scalatra" %% "scalatra-scalatest" % "2.0.0.M2" % "test"

	val jetty6 = "org.mortbay.jetty" % "jetty" % "6.1.14" % "test"
	
	val scalaCompiler = "org.scala-lang" % "scala-compiler" % "2.8.1"
	
	val servletApi = "javax.servlet" % "servlet-api" % "2.5" % "provided"
	val scalatra = "org.scalatra" %% "scalatra" % "2.0.0.M2"
  
  val scalatraFileUpload = "org.scalatra" %% "scalatra-fileupload" % "2.0.0.M2"
  	//val scalatraScalate = "org.scalatra" %% "scalatra-scalate" % "2.0.0.M1"

	val commons = "com.recursivity" % "recursivity-commons_2.8.1" % "0.2"
  	val scalate = "org.fusesource.scalate" % "scalate-core" % "1.3"

  	val sfl4japi = "org.slf4j" % "slf4j-api" % slf4jVersion
  	val sfl4jnop = "org.slf4j" % "slf4j-nop" % slf4jVersion % "runtime"


  val liftJson = "net.liftweb" % "lift-json_2.8.1" % "2.2-RC1"

	val sonatypeNexusSnapshots = "Sonatype Nexus Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots"
	val sonatypeNexusReleases = "Sonatype Nexus Releases" at "https://oss.sonatype.org/content/repositories/releases"

  	val scalateRepo = "scalate repo" at "http://repo.fusesource.com/nexus/content/repositories/public/"

  val scalaToolsRepo = "Scala-Tools repo" at "http://scala-tools.org/repo-releases/"
}