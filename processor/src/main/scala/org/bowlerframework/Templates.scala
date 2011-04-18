package org.bowlerframework

import sbt._
import sbt.processor.BasicProcessor
import org.lifty.engine._
import org.lifty.util.TemplateHelper._
import net.liftweb.common._
import org.bowlerframework.BowlerHelper._

object CONSTANTS {
  val BOWLERVERSION = "0.2.1" 
  val CONTROLLERNAME = "MyController"
  val MAINPACKAGE = "main"
}

trait DefaultBowlerTemplate extends Template with Create with Delete

object BlankBowlerProject extends DefaultBowlerTemplate {

  def name = "project-blank"

  //Arguments
  object bowlerversion extends Argument("bowlerversion") with Default with Value { value = Full(CONSTANTS.BOWLERVERSION) }

  object mainpackage extends PackageArgument("mainpackage") with Default with Value { value = Full(CONSTANTS.MAINPACKAGE)}
  
  object controllername extends Argument("controllername") with Default with Value { value = Full(CONSTANTS.CONTROLLERNAME)}
  
  def description = "Creates a blank Bowler project that uses SBT as it's build system"

  def arguments = bowlerversion :: mainpackage :: controllername :: Nil

  def files = {
    val blankProjectPath = "%s/blank-bowler-project".format(GlobalConfiguration.rootResources)
    val controllername = arguments(1)
    TemplateFile("%s/Project.ssp".format(blankProjectPath), "project/build/Project.scala") ::
      TemplateFile("%s/default.mustache".format(blankProjectPath), "src/main/resources/layouts/default.mustache") ::
      TemplateFile("%s/index.mustache".format(blankProjectPath), "src/main/resources/views/GET/index.mustache") ::
      TemplateFile("%s/Bootstrap.ssp".format(blankProjectPath), "src/main/scala/${mainpackage}/Bootstrap.scala") ::
      TemplateFile("%s/MyController.ssp".format(blankProjectPath), "src/main/scala/${mainpackage}/${controllername}.scala") ::
      TemplateFile("%s/web.ssp".format(blankProjectPath), "src/main/webapp/WEB-INF/web.xml") ::
      Nil
  }

  override def postRenderAction(arguments: List[ArgumentResult]): Unit = {
    createFolderStructure(arguments)(BowlerHelper.bowlerFolderStructure: _*)
  }

}
