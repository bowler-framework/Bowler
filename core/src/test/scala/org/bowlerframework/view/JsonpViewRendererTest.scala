package org.bowlerframework.view

import org.scalatest.FunSuite
import java.io.StringWriter
import org.bowlerframework.jvm.{DummyRequest, DummyResponse}
import org.bowlerframework.GET
import collection.mutable.MutableList

/**
 * Created by IntelliJ IDEA.
 * User: noel
 * Date: 15/07/11
 * Time: 14:11
 * To change this template use File | Settings | File Templates.
 */

class JsonpViewRendererTest extends FunSuite {

  val renderer = JsonpViewRenderer()

  test("returns wrapped json data with callback function") {
    val (writer, response) = renderJsonp()

    assert( "mycallback({\"id\":1,\"numbers\":[1,2,3,4,5,6]})".equals(writer.toString))
  }

  test("returns expected http response and header") {
    val (writer, response) = renderJsonp()

    assert(200 == response.getStatus)
    assert(response.getHeader("Content-Type") == "application/javascript")
  }

 private def renderJsonp() =  {
   val writer = new StringWriter
   val resp = new DummyResponse(writer)

   val jscriptFunction = "mycallback"
   renderer.renderView(new DummyRequest(GET, "/", Map("callback" -> jscriptFunction), null, Map("accept" -> "application/javascript")), resp, toSeq(new Winner(1, List(1, 2, 3, 4, 5, 6))))
   (writer, resp)
 }

  def toSeq(models: Any*): Seq[Any] = models.toSeq
}