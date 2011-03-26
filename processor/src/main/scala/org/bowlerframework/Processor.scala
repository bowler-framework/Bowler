package org.bowlerframework

import sbt._
import org.lifty.engine._


class Processor extends SBTTemplateProcessor {
   //def templates = MyTemplate :: Nil
   def templates = List(BlankBowlerProject)
}

/*
object MyTemplate extends Template with Create {

  
 
  def name = "hello" 

  def description = "Creates a file with a greeting in it"

  def arguments = Argument("name") :: Nil

  def files = TemplateFile( 
    "%s/greetings.ssp".format(GlobalConfiguration.rootResources), 
    "output/greetings/${name}.txt"
    ) :: Nil
 
}
 */
 
