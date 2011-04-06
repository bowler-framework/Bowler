package org.bowlerframework.view.squery

import org.scalatest.FunSuite
import java.io.StringWriter
import stub.{ComposedPageComponent, SimpleTransformingComponent, MySimpleComponent}
import org.bowlerframework.jvm.{DummyRequest, DummyResponse}
import org.bowlerframework.GET

/**
 * Created by IntelliJ IDEA.
 * User: wfaler
 * Date: 14/03/2011
 * Time: 00:15
 * To change this template use File | Settings | File Templates.
 */

class SqueryRendererTest extends FunSuite {

  test("test render") {
    val writer = new StringWriter
    SqueryRenderer.render(new MySimpleComponent, new DummyRequest(GET, "/hello/", Map(), null), new DummyResponse(writer))
    assert(writer.toString.contains("<title>A Title</title>"))
  }

  test("test performance") {
    val start = System.currentTimeMillis
    var split = System.currentTimeMillis
    for (i <- 0 until 1002) {
      val writer = new StringWriter
      SqueryRenderer.render(new ComposedPageComponent(new SimpleTransformingComponent), new DummyRequest(GET, "/hello/", Map(), null), new DummyResponse(writer))
      if (i == 1)
        split = System.currentTimeMillis
    }
    val done = System.currentTimeMillis

    println("1002 components took: " + (done - start))
    println("1000 components took: " + (split - start))

  }
}