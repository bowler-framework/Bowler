package org.bowlerframework.model

import org.scalatest.FunSuite
import org.bowlerframework.jvm.DummyRequest
import java.util.Date
import org.bowlerframework.{GET, POST}
import com.recursivity.commons.bean.GenericTypeDefinition

/**
 * Created by IntelliJ IDEA.
 * User: wfaler
 * Date: 27/12/2010
 * Time: 17:38
 * To change this activeLayout use File | Settings | File Templates.
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

  test("unmarshall child with nameHint - GenericTypeDef") {
    val address = mapper.getValueWithTypeDefinition(
      GenericTypeDefinition("org.bowlerframework.model.Address", None), makeRequest(json), "address").asInstanceOf[Address]
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


  test("without nameHint - GenericTypeDef") {
    val person = mapper.getValueWithTypeDefinition(
      GenericTypeDefinition("org.bowlerframework.model.Person", None), makeRequest(json)).asInstanceOf[Person]
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

  test("Option with result Some") {
    val address = mapper.getValue[Option[Address]](makeRequest(json), "address")
    assert(address != null)
    assert(address.get.isInstanceOf[Address])
    assert(address.get.street == "Bulevard")
    assert(address.get.city == "Helsinki")

  }

  test("Option with result None") {
    val address = mapper.getValue[Option[Address]](makeRequest("{}"), "address")
    assert(address != null)
    assert(address == None)
  }

  test("Option with result Some - GenericTypeDef") {
    val address = mapper.getValueWithTypeDefinition(optTypeDef, makeRequest(json), "address").asInstanceOf[Option[Address]]
    assert(address != null)
    assert(address.get.isInstanceOf[Address])
    assert(address.get.street == "Bulevard")
    assert(address.get.city == "Helsinki")
  }

  test("Option with result None - GenericTypeDef") {
    val address = mapper.getValueWithTypeDefinition(optTypeDef, makeRequest("{}"), "address").asInstanceOf[Option[Address]]
    assert(address != null)
    assert(address == None)
  }

  test("BigDecimal with JDouble") {
    val cash = mapper.getValue[Money](makeRequest(money))
    assert(cash.currency == "SEK")
    assert(cash.amount == new BigDecimal(new java.math.BigDecimal("3.14")))

  }

  test("BigDecimal with JInt") {
    val cash = mapper.getValue[Money](makeRequest(intMoney))
    assert(cash.currency == "SEK")
    assert(cash.amount == new BigDecimal(new java.math.BigDecimal("3")))

  }

  test("BigDecimal with JString") {
    val cash = mapper.getValue[Money](makeRequest(stringMoney))
    assert(cash.currency == "SEK")
    assert(cash.amount == new BigDecimal(new java.math.BigDecimal("3.14")))

  }

  val optTypeDef = GenericTypeDefinition("scala.Option", Some(List(GenericTypeDefinition("org.bowlerframework.model.Address", None))))


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
  val money = """
         { "currency": "SEK",
           "amount": 3.14
         }
       """

  val intMoney = """
         { "currency": "SEK",
           "amount": 3
         }
       """

  val stringMoney = """
         { "currency": "SEK",
           "amount": "3.14"
         }
       """

}

case class Money(currency: String, amount: BigDecimal) {

}

case class Child(name: String, age: Int, birthdate: Option[java.util.Date])

case class Address(street: String, city: String)

case class Person(name: String, address: Address, children: List[Child])

case class Employer(name: String)