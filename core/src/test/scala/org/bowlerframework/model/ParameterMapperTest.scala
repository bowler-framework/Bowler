package org.bowlerframework.model

import org.scalatest.FunSuite
import org.bowlerframework.jvm.DummyRequest
import org.bowlerframework.{HTTP}

/**
 * Created by IntelliJ IDEA.
 * User: wfaler
 * Date: 19/12/2010
 * Time: 22:59
 * To change this template use File | Settings | File Templates.
 */

class ParameterMapperTest extends FunSuite with ParameterMapper {

  test("1 param function signatures of java primitives") {
    val request = makeRequest(Map("string" -> "hello", "int" -> "23", "int2" -> "17"))

    mapRequest[Int](request, "int")(i => assert(i == 23))
    mapRequest[Int](request, null)(i => assert((i == 17) || (i == 23)))
  }

  test("mapper with null") {
    val request = makeRequest(Map("string" -> "hello"))
    mapRequest[Option[String]](request, null)(i => println("String is " + i))

    try {
      mapRequest[Int](makeRequest(Map()), null)(i => {
        println(i + " IS NONE: " + (None == i))
      })
      fail("this should have thrown a RequestMapperException")
    } catch {
      case e: RequestMapperException => println("expected for not using Option")
    }

  }

  test("test 2 params") {
    val request = makeRequest(Map("string" -> "hello", "int" -> "2"))
    mapRequest[Int, String](request, List())((a, b) => {
      println(a + b)
      assert(a == 2)
      assert(b == "hello")
    })
  }

  test("test 2 params - wrong number of hints") {
    val request = makeRequest(Map("string" -> "hello", "int" -> "2"))
    try {
      mapRequest[Int, String](request, List("int"))((a, b) => {
        println(a + b)
        assert(a == 2)
        assert(b == "hello")
      })
      fail("should throw IllegalArgumentException")
    } catch {
      case e: IllegalArgumentException => {} //expected}
    }

  }

  test("test 3 params") {
    val request = makeRequest(Map("String.string" -> "hello", "int" -> "2", "BigDecimal.decimal" -> "3.14"))
    mapRequest[Int, String, BigDecimal](request, List())((a, b, c) => {
      println(a + " : " + b + " : " + c)
      assert(a == 2)
      assert(b == "hello")
      assert(c == new BigDecimal(new java.math.BigDecimal("3.14")))
    })
  }

  test("test 4 params") {
    val request = makeRequest(Map("String.string" -> "hello", "int" -> "2", "BigDecimal.decimal" -> "3.14", "bool" -> "true"))
    mapRequest[Int, String, BigDecimal, Boolean](request, List())((a, b, c, d) => {
      println(a + " : " + b + " : " + c)
      assert(a == 2)
      assert(b == "hello")
      assert(c == new BigDecimal(new java.math.BigDecimal("3.14")))
      assert(d)
    })
  }

  test("test 5 params") {
    val request = makeRequest(Map("String.string" -> "hello", "Integer.int" -> "2", "BigDecimal.decimal" -> "3.14", "bool" -> "true", "Long.long" ->5))
    mapRequest[java.lang.Integer, String, BigDecimal, Boolean, java.lang.Long](request, List())((a, b, c, d,e) => {
      println(a + " : " + b + " : " + c)
      assert(a == 2)
      assert(b == "hello")
      assert(c == new BigDecimal(new java.math.BigDecimal("3.14")))
      assert(d)
      assert(e == 5l)
    })
  }

  test("test 6 params") {
    val request = makeRequest(Map("String.string" -> "hello", "Integer.int" -> "2", "BigDecimal.decimal" -> "3.14", "bool" -> "true",
      "Long.long" ->5, "List[String].list" -> List("hello", "world")))
    mapRequest[java.lang.Integer, String, BigDecimal, Boolean, java.lang.Long, List[String]](request, List())((a, b, c, d,e, f) => {
      println(a + " : " + b + " : " + c)
      assert(a == 2)
      assert(b == "hello")
      assert(c == new BigDecimal(new java.math.BigDecimal("3.14")))
      assert(d)
      assert(e == 5l)
      assert(f.size == 2)
      assert(f(0) == "hello")
      assert(f(1) == "world")
    })
  }

  test("test 7 params") {
    val request = makeRequest(Map("String.string" -> "hello", "Integer.int" -> "2", "BigDecimal.decimal" -> "3.14", "bool" -> "true",
      "Long.long" ->5, "List[String].list" -> List("hello", "world"), "Set[String].set" -> List("hello")))
    mapRequest[java.lang.Integer, String, BigDecimal, Boolean, java.lang.Long, List[String], Set[String]](request, List())((a, b, c, d, e, f, g) => {
      println(a + " : " + b + " : " + c)
      assert(a == 2)
      assert(b == "hello")
      assert(c == new BigDecimal(new java.math.BigDecimal("3.14")))
      assert(d)
      assert(e == 5l)
      assert(f.size == 2)
      assert(f(0) == "hello")
      assert(f(1) == "world")
      println("SET: " + g)
      assert(g.size == 1)
    })
  }

  //test("type safety")


  def makeRequest(params: Map[String, Any]) = new DummyRequest(HTTP.GET, "/", params, null)

}