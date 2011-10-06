package org.bowlerframework.controller

import org.scalatra.test.scalatest.ScalatraFunSuite
import org.scalatest.matchers.ShouldMatchers
import org.bowlerframework.http.BowlerFilter
import net.liftweb.json._
import org.bowlerframework.view.json.BigDecimalSerializer
import org.bowlerframework.RequestScope

case class Extracted (param1:Option[Int], param2:Option[Int])

class ExtractorBugController extends Controller with FunctionNameConventionRoutes {
  def `POST /extract`(param1:Option[Int], param2:Option[Int]) = {
    println(RequestScope.request.getParameterMap)
    println(param1 + " " + param2)
    Extracted(param1, param2)
  }
}

class ExtractingOptionalParametersTest extends ScalatraFunSuite with ShouldMatchers {
  val holder = this.addFilter(classOf[BowlerFilter], "/*")

  test("Routing and serialisation for analysising commonality in a patient list") {
   // val bootstrap = new DefaultBowlerConfiguration with Transformers
    val sut = new ExtractorBugController

    post("/extract?param1=999", "", Map("accept" -> "application/json")) {

     // import JsonSerialisationFormats._
      implicit val formats:  Formats = (net.liftweb.json.DefaultFormats + new BigDecimalSerializer)

      val results = parse(body).extract[Extracted]
      val expectedResult = Extracted(Some(999), None)
      results should be (expectedResult)
    }
  }
}
