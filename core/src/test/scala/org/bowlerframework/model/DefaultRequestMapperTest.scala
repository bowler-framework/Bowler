package org.bowlerframework.model


import org.scalatest.FunSuite
import collection.mutable.HashMap
import com.recursivity.commons.bean.{StringValueTransformer, TransformerRegistry}
import org.bowlerframework.HTTP
import org.bowlerframework.jvm.DummyRequest

/**
 * Created by IntelliJ IDEA.
 * User: wfaler
 * Date: 21/12/2010
 * Time: 22:06
 * To change this template use File | Settings | File Templates.
 */

class DefaultRequestMapperTest extends FunSuite {



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
    val value = mapper.getValue[BigDecimal](makeRequest(Map("decimal" -> "43.5")))
    assert(value == new BigDecimal(new java.math.BigDecimal("43.5")))

  }

  test("primitives") {
    val value = mapper.getValue[Int](makeRequest(Map("decimal" -> "43")))
    assert(43 == value)

    val l =  mapper.getValue[Long](makeRequest(Map("decimal" -> "43")))
    assert(43l == l)
  }

  test("update bean that has Transformer"){
    TransformerRegistry.registerTransformer(classOf[MyBean], classOf[MyBeanTransformer])
    val myBean =  mapper.getValue[MyBean](makeRequest(Map("id" -> "1", "name" -> "otherBean")))
    assert(myBean != null)
    assert(myBean.id == 1l)
    println("Decimal is: " + myBean.name)
    assert(myBean.decimal == new BigDecimal(new java.math.BigDecimal("54.4")))
    assert(myBean.name == "otherBean")
  }

  test("bean without Transformer"){
    TransformerRegistry.registerTransformer(classOf[MyBean], classOf[MyBeanTransformer])
    val myBean =  mapper.getValue[OtherMapperBean](makeRequest(Map("id" -> "1", "name" -> "otherBean", "decimal" -> "3.14", "beans" -> List("1"))))
    assert(myBean != null)
    assert(myBean.id == 1l)
    assert(myBean.decimal == new BigDecimal(new java.math.BigDecimal("3.14")))
    assert(myBean.name == "otherBean")
    assert(myBean.beans.size == 1)
    val bean = myBean.beans(0)

    assert(bean != null)
    assert(bean.id == 1l)
    assert(bean.decimal == new BigDecimal(new java.math.BigDecimal("54.4")))
    assert(bean.name == "someBean")

  }


  test("alias parameters"){
    TransformerRegistry.registerTransformer(classOf[MyBean], classOf[MyBeanTransformer])
    val request = makeRequest(Map("OtherMapperBean.id" -> "1", "OtherMapperBean.name" -> "OtherBean", "OtherMapperBean.decimal" -> "3.14",
      "OtherMapperBean.beans" -> List("1"), "MyBean.id" -> "2", "MyBean.name" -> "some beany", "MyBean.decimal" -> "57.12"))
    val bean =  mapper.getValue[OtherMapperBean](request)
    val myBean = mapper.getValue[MyBean](request)

    assert(bean.isInstanceOf[OtherMapperBean])
    assert(bean != null)
    assert(bean.id == 1l)
    assert(bean.decimal == new BigDecimal(new java.math.BigDecimal("3.14")))
    assert(bean.name == "OtherBean")
    assert(bean.beans.size == 1)
    val child = bean.beans(0)

    assert(child != null)
    assert(child.id == 1l)
    assert(child.decimal == new BigDecimal(new java.math.BigDecimal("54.4")))
    assert(child.name == "someBean")

    assert(myBean.isInstanceOf[MyBean])
    assert(myBean.id == 2l)
    assert(myBean.decimal == new BigDecimal(new java.math.BigDecimal("57.12")))
    assert(myBean.name == "some beany")

  }


  // test Option[]
  // test List[]
  // test Seq[]
  // test Set[]
  // test java.util.Collection

  def makeRequest(params: Map[String, Any]) = new DummyRequest(HTTP.GET, "/", params, null)

}

case class MyBean(id: Long, name: String, decimal: BigDecimal)
case class OtherMapperBean(id: Long, name: String, decimal: BigDecimal, beans: List[MyBean])


class MyBeanTransformer extends StringValueTransformer{
  def toValue(from: String): AnyRef ={
    if(from.equals("1"))
      MyBean(1l, "someBean", new BigDecimal(new java.math.BigDecimal("54.4")))
    else
      null
  }
}





 class A
 class B extends A