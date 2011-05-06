package org.bowlerframework.view

import org.scalatest.FunSuite
import org.bowlerframework.jvm.DummyRequest
import scalate.ScalateViewRenderer
import org.bowlerframework.exception.HttpException
import org.bowlerframework.{BowlerConfigurator, GET}

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
    val request = new DummyRequest(GET, "/", Map(), null, Map("accept" -> "application/json;charset=UTF-8"))
    val viewRenderer = BowlerConfigurator.resolveViewRenderer(request)
    assert(viewRenderer != null)
    assert(viewRenderer.isInstanceOf[JsonViewRenderer])
    BowlerConfigurator.setRenderStrategy(new DefaultRenderStrategy)
  }

  test("get html renderer") {
    BowlerConfigurator.setRenderStrategy(new StrictRenderStrategy)
    val request = new DummyRequest(GET, "/", Map(), null, Map("accept" -> "text/html"))
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
    val request = new DummyRequest(GET, "/", Map(), null, Map("accept" -> "application/xml,application/json"))
    val viewRenderer = BowlerConfigurator.resolveViewRenderer(request)
    assert(viewRenderer != null)
    assert(viewRenderer.isInstanceOf[JsonViewRenderer])
    BowlerConfigurator.setRenderStrategy(new DefaultRenderStrategy)
  }

  test("no Accept header") {
    BowlerConfigurator.setRenderStrategy(new StrictRenderStrategy)
    val request = new DummyRequest(GET, "/", Map(), null, Map())
    assert(BowlerConfigurator.resolveViewRenderer(request).isInstanceOf[JsonViewRenderer])
    BowlerConfigurator.setRenderStrategy(new DefaultRenderStrategy)
  }

  test("no matching renderer") {
    BowlerConfigurator.setRenderStrategy(new StrictRenderStrategy)
    val request = new DummyRequest(GET, "/", Map(), null, Map("accept" -> "application/xml"))
    assert(BowlerConfigurator.resolveViewRenderer(request).isInstanceOf[JsonViewRenderer])

    BowlerConfigurator.setRenderStrategy(new DefaultRenderStrategy)
  }
  BowlerConfigurator.setRenderStrategy(new DefaultRenderStrategy)
}