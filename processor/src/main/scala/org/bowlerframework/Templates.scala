package org.bowlerframework

import sbt._
import sbt.processor.BasicProcessor
import org.lifty.engine._
import org.lifty.util.TemplateHelper._
import net.liftweb.common._
import org.bowlerframework.BowlerHelper._

object CONSTANTS {
  val BOWLERVERSION = "0.2.1"
}

//Arguments with defaults	
object bowlerversion extends Argument("bowlerversion") with Default with Value { value = Full(CONSTANTS.BOWLERVERSION) }

object pack extends PackageArgument("libpack") with Default with Value {
  value = searchForPackageInBoot("src/main/scala/bootstrap/liftweb/Boot.scala", Full(".lib"))
}

trait DefaultBowlerTemplate extends Template with Create with Delete {
	lazy val defaultMainPackage = searchForMainPackage match {
		case Full(packageName) => Full(packageName)
		case Empty => Empty
		case Failure(msg,_,_) => Failure(msg)
	}
}

object BlankBowlerProject extends DefaultBowlerTemplate {

  def name = "project-blank"

  def description = "Creates a blank Bowler project that uses SBT as it's build system"

  def arguments = pack :: bowlerversion :: Nil

  def files = {
    val blankProjectPath = "%s/blank-bowler-project".format(GlobalConfiguration.rootResources)
    TemplateFile("%s/Project.ssp".format(blankProjectPath), "project/build/Project.scala") ::
      TemplateFile("%s/default.mustache".format(blankProjectPath), "src/main/resources/layouts/default.mustache") ::
      TemplateFile("%s/index.mustache".format(blankProjectPath), "src/main.resources/views/GET/index.mustache") ::
      TemplateFile("%s/Bootstrap.scala".format(blankProjectPath), "src/main/scala/bowlerquickstart/Bootstrap.scala") ::
      TemplateFile("%s/MyController.scala".format(blankProjectPath), "src/main/scala/bowlerquickstart/MyController.scala") ::
      TemplateFile("%s/web.xml".format(blankProjectPath), "src/webapp/WEB-INF/web.xml") ::
      Nil
  }

  override def postRenderAction(arguments: List[ArgumentResult]): Unit = {
    createFolderStructure(arguments)(BowlerHelper.bowlerFolderStructure: _*)
  }


}