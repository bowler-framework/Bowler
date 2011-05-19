package org.bowlerframework.view

import org.scalatest.FunSuite
import org.bowlerframework.jvm.DummyRequest
import org.bowlerframework.{GET, BowlerConfigurator}
import scalate.ScalateViewRenderer
import org.bowlerframework.exception.HttpException

/**
 * Created by IntelliJ IDEA.
 * User: wfaler
 * Date: 20/04/2011
 * Time: 21:24
 * To change this template use File | Settings | File Templates.
 */

class StrictRenderStrategyTest extends FunSuite {

  test("get json strategy") {
    BowlerConfigurator.setRenderStrategy(new StrictRenderStrategy)
    val request = new DummyRequest(GET, "/", Map(), null, Map("accept" -> "text/html;q=0.8, application/json"))
    val viewRenderer = BowlerConfigurator.resolveViewRenderer(request)
    assert(viewRenderer != null)
    assert(viewRenderer.isInstanceOf[JsonViewRenderer])
    BowlerConfigurator.setRenderStrategy(new DefaultRenderStrategy)
  }

  test("get html renderer") {
    BowlerConfigurator.setRenderStrategy(new StrictRenderStrategy)
    val request = new DummyRequest(GET, "/", Map(), null, Map("accept" -> "text/html, application/json;q=0.9"))
    val viewRenderer = BowlerConfigurator.resolveViewRenderer(request)
    assert(viewRenderer != null)
    assert(viewRenderer.isInstanceOf[ScalateViewRenderer])
    BowlerConfigurator.setRenderStrategy(new DefaultRenderStrategy)
  }

  test("get html renderer, application/xhtml") {
    BowlerConfigurator.setRenderStrategy(new StrictRenderStrategy)
    val request = new DummyRequest(GET, "/", Map(), null, Map("accept" -> "application/xhtml+xml"))
    val viewRenderer = BowlerConfigurator.resolveViewRenderer(request)
    assert(viewRenderer != null)
    assert(viewRenderer.isInstanceOf[ScalateViewRenderer])
    BowlerConfigurator.setRenderStrategy(new DefaultRenderStrategy)
  }

  test("get second choice renderer, no match on first") {
    BowlerConfigurator.setRenderStrategy(new StrictRenderStrategy)
    val request = new DummyRequest(GET, "/", Map(), null, Map("accept" -> "application/xml, application/json"))
    val viewRenderer = BowlerConfigurator.resolveViewRenderer(request)
    assert(viewRenderer != null)
    assert(viewRenderer.isInstanceOf[JsonViewRenderer])
    BowlerConfigurator.setRenderStrategy(new DefaultRenderStrategy)
  }


  test("no matching renderer") {
    BowlerConfigurator.setRenderStrategy(new StrictRenderStrategy)
    val request = new DummyRequest(GET, "/", Map(), null, Map("accept" -> "application/xml"))
    try{
      BowlerConfigurator.resolveViewRenderer(request)
      fail
    }catch{
      case e: HttpException => {
        assert(e.code == 406)
      }
    }
    BowlerConfigurator.setRenderStrategy(new DefaultRenderStrategy)
  }
}