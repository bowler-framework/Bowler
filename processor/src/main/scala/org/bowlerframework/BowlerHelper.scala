package org.bowlerframework

import java.util.Properties
import java.io._
import net.liftweb.common._
import scala.io.Source

object BowlerHelper  {
	
	def bowlerFolderStructure: List[String] = List(
		"src/main/resources",
		"src/main/resources/layouts",
		"src/main/resources/views",
		"src/main/resources/views/GET",
		"src/main/scala",
		"src/main/scala/${mainpackage}",
		"src/main/webapp",
		"src/main/webapp/WEB-INF")
}
