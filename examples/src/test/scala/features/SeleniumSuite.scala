package features

import org.scalatest.Suite
import org.scalatest.BeforeAndAfterAll
import support.SeleniumUtils._


class SeleniumSuite extends Suite with BeforeAndAfterAll{
	
	override def nestedSuites =
	     List(new StartPageSpec, new WidgetSpec)


  override protected def afterAll() = {
    quit
  }

	
}
