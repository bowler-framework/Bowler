import sbt._

class SampleSBTProcessor(info: ProjectInfo) extends ProcessorProject(info) {
	
	  val scalatools_release = "Scala Tools Releases" at "http://scala-tools.org/repo-releases/"
	  val scalatools_snapshots = "Scala Tools Snapshot" at "http://scala-tools.org/repo-snapshots/"

	  val sbt_template_engine = "org.lifty" %% "lifty-engine" % "0.5"
	  val scalatest = "org.scalatest" % "scalatest" % "1.0" % "test"

	  override def managedStyle = ManagedStyle.Maven
	  val publishTo = "Scala Tools Nexus" at "http://nexus.scala-tools.org/content/repositories/releases/"
	  Credentials(Path.userHome / "dev" / ".nexus_credentials", log)

	  override def mainClass: Option[String] = Some("org.lifty.processor.LiftyStandAlone")
}
