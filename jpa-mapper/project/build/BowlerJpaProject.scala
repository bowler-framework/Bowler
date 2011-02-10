import sbt._


class BowlerJpaProject(info: ProjectInfo) extends DefaultProject(info){
	  val bowler = "org.bowlerframework" % "persistence-mapper_2.8.1" % "0.2-SNAPSHOT"

	  val slf4jVersion = "1.6.0"

	  val sfl4jnop = "org.slf4j" % "slf4j-nop" % slf4jVersion % "test"

      //val c3p0 = "c3p0" % "c3p0" % "0.9.1.2"
	
	  val squeryl = "com.recursivity" % "recursivity-jpa_2.8.1" % "1.0-SNAPSHOT"

	  val h2database = "com.h2database" % "h2" % "1.2.144" % "test"
	
		val jbossRepo = "JBoss repo" at "http://repository.jboss.com/maven2/"

	  val sonatypeNexusSnapshots = "Sonatype Nexus Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots" 
	  val sonatypeNexusReleases = "Sonatype Nexus Releases" at "https://oss.sonatype.org/content/repositories/releases"

	  val scalaToolsRepo = "Scala-Tools repo" at "http://scala-tools.org/repo-releases/"
}