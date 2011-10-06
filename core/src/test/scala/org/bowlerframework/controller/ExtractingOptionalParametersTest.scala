package uk.ac.rvc.veqel.controllers

import org.scalatra.test.scalatest.ScalatraFunSuite
import org.scalatest.matchers.ShouldMatchers
import org.bowlerframework.http.BowlerFilter
import uk.ac.rvc.veqel.startup.transformers.Transformers
import org.bowlerframework.controller.{Controller, FunctionNameConventionRoutes}
import uk.ac.rvc.veqel.bowler.{JsonSerialisationFormats, DefaultBowlerConfiguration}
import net.liftweb.json._

case class Extracted (param1:Option[Int], param2:Option[Int])

class ExtractorController extends Controller with FunctionNameConventionRoutes {
  def `GET /extract`(param1:Option[Int], param2:Option[Int]) = {
    Extracted(param1, param2)
  }
}

class ExtractingOptionalParametersTest extends ScalatraFunSuite with ShouldMatchers {
  val holder = this.addFilter(classOf[BowlerFilter], "/*")

  test("Routing and serialisation for analysising commonality in a patient list") {
    val bootstrap = new DefaultBowlerConfiguration with Transformers
    val sut = new ExtractorBugController

    get("/extract?param1=999") {

      import JsonSerialisationFormats._

      val results = parse(body).extract[Extracted]
      val expectedResult = Extracted(Some(999), None)
      results should be (expectedResult)
    }
  }
}
