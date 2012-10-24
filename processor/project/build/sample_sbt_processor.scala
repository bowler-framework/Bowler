import sbt._

class SampleSBTProcessor(info: ProjectInfo) extends ProcessorProject(info) {
	
	  val sbt_template_engine = "org.lifty" %% "lifty-engine" % "0.5"
	  val scalatest = "org.scalatest" % "scalatest" % "1.0" % "test"

	  override def managedStyle = ManagedStyle.Maven

	  override def mainClass: Option[String] = Some("org.lifty.processor.LiftyStandAlone")
}
