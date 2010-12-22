package org.bowlerframework

import jvm.DummyRequest
import org.scalatest.FunSuite
import collection.mutable.HashMap
import com.recursivity.commons.bean.TransformerRegistry

/**
 * Created by IntelliJ IDEA.
 * User: wfaler
 * Date: 21/12/2010
 * Time: 22:06
 * To change this template use File | Settings | File Templates.
 */

class DefaultRequestMapperTest extends FunSuite {
  // test normal values
  // test beans
  // test Option[]
  // test List[]
  // test Seq[]
  // test Set[]
  // test java.util.Collection

  val mapper = new DefaultRequestMapper

  test("string bug that should throw ClassCastException") {
    try {
      val v = mapper.getValue[String](makeRequest(Map()))
      fail("Should throw ClassCastException")
    } catch {
      case e: ClassCastException => {} // do nothing, expected}
    }
  }

  test("assignability"){
    assert(classOf[A].isAssignableFrom(classOf[B]))
    assert(!classOf[B].isAssignableFrom(classOf[A]))

    assert(classOf[Seq[_]].isAssignableFrom(classOf[List[String]]))
  }

  test("BigDecimal") {

  }

  test("primitives") {

  }


  def makeRequest(params: Map[String, Any]) = new DummyRequest(HTTP.GET, "/", params, null)

}

 class A
 class B extends A