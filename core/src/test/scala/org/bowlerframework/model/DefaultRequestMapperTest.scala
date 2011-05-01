package org.bowlerframework.model


import org.scalatest.FunSuite
import org.bowlerframework.jvm.DummyRequest
import org.bowlerframework.{GET, POST, HttpMethod}
import com.recursivity.commons.bean.{GenericTypeDefinition, StringValueTransformer, TransformerRegistry}

/**
 * Created by IntelliJ IDEA.
 * User: wfaler
 * Date: 21/12/2010
 * Time: 22:06
 * To change this activeLayout use File | Settings | File Templates.
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

  test("assignability") {
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

    val l = mapper.getValue[Long](makeRequest(Map("decimal" -> "43")))
    assert(43l == l)
  }

  test("update bean that has Transformer") {
    TransformerRegistry.registerTransformer(classOf[MyBean], classOf[MyBeanTransformer])
    val myBean = mapper.getValue[MyBean](makeRequest(Map("id" -> "1", "name" -> "otherBean")))
    assert(myBean != null)
    assert(myBean.id == 1l)
    println("Decimal is: " + myBean.name)
    assert(myBean.decimal == new BigDecimal(new java.math.BigDecimal("54.4")))
    assert(myBean.name == "otherBean")
  }

  test("bean without Transformer") {
    TransformerRegistry.registerTransformer(classOf[MyBean], classOf[MyBeanTransformer])
    val myBean = mapper.getValue[OtherMapperBean](makeRequest(Map("id" -> "1", "name" -> "otherBean", "decimal" -> "3.14", "beans" -> List("1"))))
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


  test("alias parameters") {
    TransformerRegistry.registerTransformer(classOf[MyBean], classOf[MyBeanTransformer])
    val request = makeRequest(Map("otherMapperBean.id" -> "1", "otherMapperBean.name" -> "otherBean", "otherMapperBean.decimal" -> "3.14",
      "otherMapperBean.beans" -> List("1"), "myBean.id" -> "2", "myBean.name" -> "some beany", "myBean.decimal" -> "57.12"))
    val bean = mapper.getValue[OtherMapperBean](request)
    val myBean = mapper.getValue[MyBean](request)

    assert(bean.isInstanceOf[OtherMapperBean])
    assert(bean != null)
    assert(bean.id == 1l)
    assert(bean.decimal == new BigDecimal(new java.math.BigDecimal("3.14")))
    assert(bean.name == "otherBean")
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


  test("alias parameters with GET (should not create new beans)") {
    TransformerRegistry.registerTransformer(classOf[MyBean], classOf[MyBeanTransformer])
    val map = Map("otherMapperBean.id" -> "1", "otherMapperBean.name" -> "otherBean", "otherMapperBean.decimal" -> "3.14",
      "otherMapperBean.beans" -> List("1"), "myBean.id" -> "2", "myBean.name" -> "some beany", "myBean.decimal" -> "57.12")

    val request = new DummyRequest(GET, "/", map, null)

    try {
      val bean = mapper.getValue[OtherMapperBean](request)
    } catch {
      case e: ClassCastException => {} // expected
    }
  }

  test("alias parameters with transformer GET (should not create new beans)") {
    TransformerRegistry.registerTransformer(classOf[MyBean], classOf[MyBeanTransformer])
    val map = Map("otherMapperBean.id" -> "1", "otherMapperBean.name" -> "otherBean", "otherMapperBean.decimal" -> "3.14",
      "otherMapperBean.beans" -> List("1"), "myBean.id" -> "2", "myBean.name" -> "some beany", "myBean.decimal" -> "57.12")

    val request = new DummyRequest(GET, "/", map, null)

    try {
      val myBean = mapper.getValue[MyBean](request)
    } catch {
      case e: ClassCastException => {} // expected
    }
  }

  test("alias parameters with transformer hit GET (should not edit any values)") {
    TransformerRegistry.registerTransformer(classOf[MyBean], classOf[MyBeanTransformer])
    val myBean = mapper.getValue[MyBean](makeRequest(GET, Map("id" -> "1", "name" -> "otherBean")))
    assert(myBean != null)
    assert(myBean.id == 1l)
    println("Decimal is: " + myBean.name)
    assert(myBean.decimal == new BigDecimal(new java.math.BigDecimal("54.4")))
    assert(myBean.name == "someBean")
  }

  test("alias parameters with int and String") {
    val value = mapper.getValue[java.lang.Integer](makeRequest(Map("string.id" -> "43", "integer.id" -> "34")))
    println(value)
    assert(value == 34)

    assert("43" == mapper.getValue[String](makeRequest(Map("String.id" -> "43", "Int.id" -> "34"))))

  }

  test("test Transient marker trait") {
    val bean = mapper.getValue[TransientBean](makeRequest(GET, Map("id" -> "43", "name" -> "transientBean")))
    assert(bean != null)
    assert(bean.isInstanceOf[TransientBean])
    assert(bean.id == 43l)
    assert(bean.name == "transientBean")

  }


  test("Option with value") {
    val holder = mapper.getValue[Option[TransientBean]](makeRequest(GET, Map("id" -> "43", "name" -> "transientBean")))
    assert(holder.isInstanceOf[Some[TransientBean]])
    val bean = holder.get
    assert(bean != null)
    assert(bean.isInstanceOf[TransientBean])
    assert(bean.id == 43l)
    assert(bean.name == "transientBean")
  }

  test("Option Without Value") {
    val map = Map("otherMapperBean.id" -> "1", "otherMapperBean.name" -> "otherBean", "otherMapperBean.decimal" -> "3.14",
      "otherMapperBean.beans" -> List("1"), "myBean.id" -> "2", "myBean.name" -> "some beany", "myBean.decimal" -> "57.12")

    val request = new DummyRequest(GET, "/", map, null)
    assert(None.equals(mapper.getValue[Option[MyBean]](request)))
  }

  test("Option with Nested List") {
    TransformerRegistry.registerTransformer(classOf[MyBean], classOf[MyBeanTransformer])
    val map = Map("name" -> "somename", "beans" -> List("1"))
    val request = makeRequest(map)
    val beansOption = mapper.getValue[Option[List[MyBean]]](request, "beans")
    assert(beansOption.isInstanceOf[Option[List[MyBean]]])
    val beans = beansOption.get
    assert(beans.size == 1)
    assert(beans(0).isInstanceOf[MyBean])

    val bean = beans(0)
    assert(bean.id == 1l)
    assert(bean.name == "someBean")
    assert(bean.decimal == new BigDecimal(new java.math.BigDecimal("54.4")))
  }

  test("Option with Nested List - with GenericTypeDefinition") {
    TransformerRegistry.registerTransformer(classOf[MyBean], classOf[MyBeanTransformer])
    val map = Map("name" -> "somename", "beans" -> List("1"))
    val request = makeRequest(map)
    val beansOption = mapper.getValueWithTypeDefinition(GenericTypeDefinition("scala.Option",
      Some(List(GenericTypeDefinition("scala.List",
        Some(List(GenericTypeDefinition("org.bowlerframework.model.MyBean",None))))))), request).asInstanceOf[Option[List[MyBean]]]
    assert(beansOption.isInstanceOf[Option[List[MyBean]]])
    val beans = beansOption.get
    assert(beans.size == 1)
    assert(beans(0).isInstanceOf[MyBean])

    val bean = beans(0)
    assert(bean.id == 1l)
    assert(bean.name == "someBean")
    assert(bean.decimal == new BigDecimal(new java.math.BigDecimal("54.4")))
  }

  test("Test List with nameHint") {
    TransformerRegistry.registerTransformer(classOf[MyBean], classOf[MyBeanTransformer])
    val map = Map("name" -> "somename", "beans" -> List("1"))
    val request = makeRequest(map)
    val beans = mapper.getValue[List[MyBean]](request, "beans")
    assert(beans.isInstanceOf[List[MyBean]])
    assert(beans.size == 1)
    assert(beans(0).isInstanceOf[MyBean])

    val bean = beans(0)
    assert(bean.id == 1l)
    assert(bean.name == "someBean")
    assert(bean.decimal == new BigDecimal(new java.math.BigDecimal("54.4")))
  }

  test("List without namehint - get first list") {
    TransformerRegistry.registerTransformer(classOf[MyBean], classOf[MyBeanTransformer])
    val map = Map("name" -> "somename", "beans" -> List("1"))
    val request = makeRequest(map)
    val beans = mapper.getValue[List[MyBean]](request)
    assert(beans.isInstanceOf[List[MyBean]])
    assert(beans.size == 1)
    assert(beans(0).isInstanceOf[MyBean])

    val bean = beans(0)
    assert(bean.id == 1l)
    assert(bean.name == "someBean")
    assert(bean.decimal == new BigDecimal(new java.math.BigDecimal("54.4")))

  }

  test("empty Option[List] should return None") {
    TransformerRegistry.registerTransformer(classOf[MyBean], classOf[MyBeanTransformer])
    val map = Map("name" -> "somename", "beans" -> List("2"))
    val request = makeRequest(GET, map)
    val beans = mapper.getValue[Option[List[MyBean]]](request)
    println(beans)
    assert(beans == None)
  }

  test("empty Option[List] should return None - with GenericTypeDefinition") {
    TransformerRegistry.registerTransformer(classOf[MyBean], classOf[MyBeanTransformer])
    val map = Map("name" -> "somename", "beans" -> List("2"))
    val request = makeRequest(GET, map)
    val beans = mapper.getValueWithTypeDefinition(GenericTypeDefinition("scala.Option",
      Some(List(GenericTypeDefinition("scala.List",
        Some(List(GenericTypeDefinition("org.bowlerframework.model.MyBean",None))))))), request).asInstanceOf[Option[List[MyBean]]]
    println(beans)
    assert(beans == None)
  }

  test("test Seq") {
    TransformerRegistry.registerTransformer(classOf[MyBean], classOf[MyBeanTransformer])
    val map = Map("name" -> "somename", "beans" -> List("1"))
    val request = makeRequest(map)
    val beans = mapper.getValue[Seq[MyBean]](request)
    assert(beans.isInstanceOf[Seq[MyBean]])
    assert(beans.size == 1)
    assert(beans(0).isInstanceOf[MyBean])

    val bean = beans(0)
    assert(bean.id == 1l)
    assert(bean.name == "someBean")
    assert(bean.decimal == new BigDecimal(new java.math.BigDecimal("54.4")))
  }


  test("test Set") {
    TransformerRegistry.registerTransformer(classOf[MyBean], classOf[MyBeanTransformer])
    val map = Map("name" -> "somename", "beans" -> List("1"))
    val request = makeRequest(map)
    val beans = mapper.getValue[Set[MyBean]](request)
    assert(beans.isInstanceOf[Set[MyBean]])
    assert(beans.size == 1)
    assert(beans.head.isInstanceOf[MyBean])

    val bean = beans.head
    assert(bean.id == 1l)
    assert(bean.name == "someBean")
    assert(bean.decimal == new BigDecimal(new java.math.BigDecimal("54.4")))
  }

  test("java.util.List") {
    TransformerRegistry.registerTransformer(classOf[MyBean], classOf[MyBeanTransformer])
    val map = Map("name" -> "somename", "beans" -> List("1"))
    val request = makeRequest(map)
    val beans = mapper.getValue[java.util.List[MyBean]](request)
    assert(beans.isInstanceOf[java.util.List[MyBean]])
    assert(beans.size == 1)
    assert(beans.get(0).isInstanceOf[MyBean])

    val bean = beans.get(0)
    assert(bean.id == 1l)
    assert(bean.name == "someBean")
    assert(bean.decimal == new BigDecimal(new java.math.BigDecimal("54.4")))
  }

  test("java.util.TreeSet") {
    TransformerRegistry.registerTransformer(classOf[MyBean], classOf[MyBeanTransformer])
    val map = Map("name" -> "somename", "beans" -> List("1"))
    val request = makeRequest(map)
    val beans = mapper.getValue[java.util.TreeSet[MyBean]](request)
    assert(beans.isInstanceOf[java.util.TreeSet[MyBean]])
    assert(beans.size == 1)
    val bean = beans.iterator.next
    assert(bean.isInstanceOf[MyBean])
    assert(bean.id == 1l)
    assert(bean.name == "someBean")
    assert(bean.decimal == new BigDecimal(new java.math.BigDecimal("54.4")))
  }


  test("java.util.TreeSet - with GenericTypeDefinition") {
    TransformerRegistry.registerTransformer(classOf[MyBean], classOf[MyBeanTransformer])
    val map = Map("name" -> "somename", "beans" -> List("1"))
    val request = makeRequest(map)
    val beans = mapper.getValueWithTypeDefinition(GenericTypeDefinition("java.util.TreeSet",
      Some(List(GenericTypeDefinition("org.bowlerframework.model.MyBean", None)))), request).asInstanceOf[java.util.TreeSet[MyBean]]
    assert(beans.isInstanceOf[java.util.TreeSet[MyBean]])
    assert(beans.size == 1)
    val bean = beans.iterator.next
    assert(bean.isInstanceOf[MyBean])
    assert(bean.id == 1l)
    assert(bean.name == "someBean")
    assert(bean.decimal == new BigDecimal(new java.math.BigDecimal("54.4")))
  }


  def makeRequest(params: Map[String, Any]) = new DummyRequest(POST, "/", params, null)

  def makeRequest(method: HttpMethod, params: Map[String, Any]) = new DummyRequest(method, "/", params, null)


}

case class TransientBean(id: Long, name: String) extends Transient

case class MyBean(id: Long, name: String, decimal: BigDecimal)

case class OtherMapperBean(id: Long, name: String, decimal: BigDecimal, beans: List[MyBean])


class MyBeanTransformer extends StringValueTransformer[MyBean] {
  def toValue(from: String): Option[MyBean] = {
    if (from.equals("1"))
      return Some(MyBean(1l, "someBean", new BigDecimal(new java.math.BigDecimal("54.4"))))
    else
      None
  }
}

class A

class B extends A