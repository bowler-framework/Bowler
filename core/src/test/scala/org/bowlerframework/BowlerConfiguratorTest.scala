package org.bowlerframework

import jvm.DummyRequest
import model.{DefaultRequestMapper, JsonRequestMapper}
import org.scalatest.FunSuite

/**
 * Created by IntelliJ IDEA.
 * User: wfaler
 * Date: 21/12/2010
 * Time: 22:14
 * To change this layout use File | Settings | File Templates.
 */

class BowlerConfiguratorTest extends FunSuite{

  test("getRequestMapper"){
    val mapper = BowlerConfigurator.getRequestMapper(new DummyRequest(HTTP.GET, "/", null, null))
    assert(mapper != null)
    assert(mapper.getClass.equals(classOf[DefaultRequestMapper]))
  }

  test("json request mapper"){
    val mapper = BowlerConfigurator.getRequestMapper(new DummyRequest(HTTP.GET, "/", null, null, Map("Content-Type" -> "application/json")))
    assert(mapper != null)
    assert(mapper.getClass.equals(classOf[JsonRequestMapper]))
  }

}