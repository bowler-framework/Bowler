package org.bowlerframework

import sbt._
import org.lifty.engine._


class Processor extends SBTTemplateProcessor {
   def templates = List(BlankBowlerProject)
}
 
