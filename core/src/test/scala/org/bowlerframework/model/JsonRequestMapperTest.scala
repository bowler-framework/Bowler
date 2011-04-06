package org.bowlerframework.model

import org.scalatest.FunSuite
import org.bowlerframework.jvm.DummyRequest
import java.util.Date
import org.bowlerframework.{GET, POST}

/**
 * Created by IntelliJ IDEA.
 * User: wfaler
 * Date: 27/12/2010
 * Time: 17:38
 * To change this layout use File | Settings | File Templates.
 */

class JsonRequestMapperTest extends FunSuite {
  val mapper = new JsonRequestMapper

  test("GET or DELETE (should fallback on DefaultRequestMapper") {
    val integer = mapper.getValue[Int](makeGetRequest(Map("int" -> "43")))
    assert(integer == 43)
  }

  test("unmarshall child with nameHint") {
    val address = mapper.getValue[Address](makeRequest(json), "address")
    assert(address != null)
    assert(address.isInstanceOf[Address])
    assert(address.street == "Bulevard")
    assert(address.city == "Helsinki")

  }

  test("without nameHint") {
    val person = mapper.getValue[Person](makeRequest(json))
    assert(person != null)
    assert(person.isInstanceOf[Person])
    assert(person.name == "joe")
    assert(person.address.street == "Bulevard")
    assert(person.address.city == "Helsinki")

    assert(person.children(0).name == "Mary")
    assert(person.children(1).name == "Mazy")

    assert(person.children(0).age == 5)
    assert(person.children(1).age == 3)

    assert(person.children(0).birthdate.get.isInstanceOf[Date])
    assert(person.children(1).birthdate == None)

    assert(person.children.size == 2)

  }

  def makeRequest(body: String) = new DummyRequest(POST, "/", Map(), body)

  def makeRequest(params: Map[String, Any]) = new DummyRequest(POST, "/", params, null)

  def makeGetRequest(params: Map[String, Any]) = new DummyRequest(GET, "/", params, null)


  val json = """
         { "name": "joe",
           "address": {
             "street": "Bulevard",
             "city": "Helsinki"
           },
           "children": [
             {
               "name": "Mary",
               "age": 5
               "birthdate": "2004-09-04T18:06:22Z"
             },
             {
               "name": "Mazy",
               "age": 3
             }
           ]
         }
       """

}

case class Child(name: String, age: Int, birthdate: Option[java.util.Date])

case class Address(street: String, city: String)

case class Person(name: String, address: Address, children: List[Child])

case class Employer(name: String)