package org.bowlerframework.view.squery

import org.scalatest.FunSuite
import org.bowlerframework.jvm.DummyResponse
import stub.MySimpleComponent
import java.io.StringWriter

/**
 * Created by IntelliJ IDEA.
 * User: wfaler
 * Date: 14/03/2011
 * Time: 00:15
 * To change this template use File | Settings | File Templates.
 */

class SqueryRendererTest extends FunSuite{

  test("test render"){
    val writer = new StringWriter
    SqueryRenderer.render(new MySimpleComponent, new DummyResponse(writer))
    assert(writer.toString.contains("<title>A Title</title>"))

  }
}