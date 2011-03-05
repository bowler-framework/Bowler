package features

import org.scalatest.Suite
import org.scalatest.BeforeAndAfterAll
import support.SeleniumUtils._


class SeleniumSuite extends Suite with BeforeAndAfterAll{
	
	override def nestedSuites =
	     List(new ExampleSpec)
	
	override def afterAll(configMap: Map[String, Any]) {
		println("ALL DONE!")
		quit
	}
	
}
