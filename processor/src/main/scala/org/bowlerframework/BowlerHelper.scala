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
		
	def searchForMainPackage: Box[String] = {
		val properties = new Properties()
		properties.load(new FileInputStream("project/build.properties"))
		properties.getProperty("project.organization")  match {
			case null => Empty
			case str => Full(str)	
		}
	}
  
  /**
   * Searches for the main package used for the app in the Boot.scala class. If 
   * the boot file doesn't exist it will search the properties file
   * 
   * @return A box with the string, if successful. 
   */
  def searchForPackageInBoot(pathToBoot: String, append: Box[String]): Box[String] = {
    val regex = """\("(\S*)"\)""".r
    val file = new File(pathToBoot)
    
    if (file.exists) {
      val is = new FileInputStream(file)
      val in = scala.io.Source.fromInputStream(is)
      in.getLines.filter(_.contains("LiftRules.addToPackages")).toList match {
          case head :: rest => regex.findFirstMatchIn(head) match {
            case Some(m) => Full(m.group(1) + append.openOr(""))
            case None => Failure("Regxp search in boot failed")
          }
          case Nil => Failure("No lines contained LiftRules.addToPackages")
      }
    
    } else {
      searchForMainPackage match {
        case Full(str) => Full(str + append.openOr(""))
        case _ => Empty
      }
    }
  }
	

}
