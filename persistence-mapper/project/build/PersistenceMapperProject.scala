import sbt._


class PersistenceMapperProject(info: ProjectInfo) extends DefaultProject(info){
	  val bowler = "org.bowlerframework" % "bowler-core_2.8.1" % "0.2-SNAPSHOT"

	  val slf4jVersion = "1.6.0"

	  val sfl4jnop = "org.slf4j" % "slf4j-nop" % slf4jVersion % "test"

  	  val c3p0 = "c3p0" % "c3p0" % "0.9.1.2" % "test"

	// allows you to use an embedded/in-JVM jetty-server to run unit-tests.
	  val scalatraTest = "org.scalatra" %% "scalatra-scalatest" % "2.0.0.M2" % "test"

	  val jetty6 = "org.eclipse.jetty" % "jetty-server" % "7.2.0.v20101020" % "test"
		val jettyWebapp = "org.eclipse.jetty" % "jetty-webapp" % "7.2.0.v20101020" % "test"

	  val servletApi = "javax.servlet" % "servlet-api" % "2.5" % "provided"
	
	  val squeryl = "org.squeryl" % "squeryl_2.8.0" % "0.9.4-RC3" % "test"

	  val h2database = "com.h2database" % "h2" % "1.2.144" % "test"

	  val sonatypeNexusSnapshots = "Sonatype Nexus Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots" 
	  val sonatypeNexusReleases = "Sonatype Nexus Releases" at "https://oss.sonatype.org/content/repositories/releases"

	  val scalaToolsRepo = "Scala-Tools repo" at "http://scala-tools.org/repo-releases/"
}