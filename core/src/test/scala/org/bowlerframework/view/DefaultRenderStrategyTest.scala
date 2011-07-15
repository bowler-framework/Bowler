package org.bowlerframework.view

import org.scalatest.FunSuite
import org.bowlerframework.jvm.DummyRequest
import scalate.ScalateViewRenderer
import org.bowlerframework.{GET, BowlerConfigurator}

/**
 * Created by IntelliJ IDEA.
 * User: wfaler
 * Date: 28/12/2010
 * Time: 23:15
 * To change this activeLayout use File | Settings | File Templates.
 */

class DefaultRenderStrategyTest extends FunSuite {
  BowlerConfigurator.setRenderStrategy(new DefaultRenderStrategy)

  test("get json strategy") {
    val request = new DummyRequest(GET, "/", Map(), null, Map("accept" -> "application/json"))
    val viewRenderer = BowlerConfigurator.resolveViewRenderer(request)
    assert(viewRenderer != null)
    assert(viewRenderer.isInstanceOf[JsonViewRenderer])
  }

  test("get html renderer") {
    val request = new DummyRequest(GET, "/", Map(), null)
    val viewRenderer = BowlerConfigurator.resolveViewRenderer(request)
    assert(viewRenderer != null)
    assert(viewRenderer.isInstanceOf[ScalateViewRenderer])
  }

   test("get jsonp strategy") {
    val request = new DummyRequest(GET, "/", Map("callback" -> "jscript_callback_function"), null, Map("accept" -> "application/javascript, text/javascript"))
    val viewRenderer = BowlerConfigurator.resolveViewRenderer(request)
    assert(viewRenderer != null)
    assert(viewRenderer.isInstanceOf[JsonpViewRenderer])
  }


}