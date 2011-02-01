package org.bowlerframework.squeryl

import dao.LongKeyedDao
import org.scalatra.test.scalatest.ScalatraFunSuite
import org.squeryl.PrimitiveTypeMode._
import org.bowlerframework.http.BowlerFilter

/**
 * Created by IntelliJ IDEA.
 * User: wfaler
 * Date: 01/02/2011
 * Time: 01:05
 * To change this template use File | Settings | File Templates.
 */

class CrudControllerTest extends ScalatraFunSuite with InMemoryDbTest{
    import Library._

  val dao = new LongKeyedDao[Author](authors)
  val holder = this.addFilter(classOf[BowlerFilter], "/*")

  test("get /"){
    val controller = new CrudController[Author, Long](dao, "authors")

    startTx

    transaction{
      dao.create(new Author(0, "1","Doe", Some("johndoe@gmail.com")))
      dao.create(new Author(0, "2","Doe", Some("johndoe@gmail.com")))
      dao.create(new Author(0, "3","Doe", Some("johndoe@gmail.com")))
      dao.create(new Author(0, "4","Doe", Some("johndoe@gmail.com")))
      dao.create(new Author(0, "5","Doe", Some("johndoe@gmail.com")))
      dao.create(new Author(0, "6","Doe", Some("johndoe@gmail.com")))
      dao.create(new Author(0, "7","Doe", Some("johndoe@gmail.com")))
      dao.create(new Author(0, "8","Doe", Some("johndoe@gmail.com")))
      dao.create(new Author(0, "9","Doe", Some("johndoe@gmail.com")))
      dao.create(new Author(0, "10","Doe", Some("johndoe@gmail.com")))
      dao.create(new Author(0, "11","Doe", Some("johndoe@gmail.com")))
      dao.create(new Author(0, "12","Doe", Some("johndoe@gmail.com")))
      dao.create(new Author(0, "13","Doe", Some("johndoe@gmail.com")))
      dao.create(new Author(0, "14","Doe", Some("johndoe@gmail.com")))
      dao.create(new Author(0, "15","Doe", Some("johndoe@gmail.com")))

      val all = dao.findAll()
      assert(all.size == 15)
      assert(all.last.firstName == "15")
      assert(all.head.firstName == "1")

      val firstTen = dao.findAll(0, 10)
      println(firstTen.size)
      assert(firstTen.size == 10)
      assert(firstTen.last.firstName == "10")
      assert(firstTen.head.firstName == "1")
      val lastFive = dao.findAll(10, 10)

      assert(lastFive.size == 5)
      assert(lastFive.last.firstName == "15")
      assert(lastFive.head.firstName == "11")

      //all.foreach(f => dao.delete(f))
    }

    commit
    var someBody: String = null
    this.get("/authors/", Seq.empty, Map("accept" -> "application/json,;q=0.9,text/plain;q=0.8,image/png,*//*;q=0.5")){
      someBody = this.body
    }

    someBody = "{\"list\":" + someBody + "}"

    val results = this.getValue[ListHolder](someBody, null)

    var secondBody: String = null

    this.get("/authors/page/1", Seq.empty, Map("accept" -> "application/json,;q=0.9,text/plain;q=0.8,image/png,*//*;q=0.5")){
      secondBody = this.body
    }

    secondBody = "{\"list\":" + secondBody + "}"

    assert(secondBody == someBody)

    assert(10 == results.list.size)
    assert(results.list(0).firstName == "1")
    assert(results.list(9).firstName == "10")

    this.get("/authors/page/2", Seq.empty, Map("accept" -> "application/json,;q=0.9,text/plain;q=0.8,image/png,*//*;q=0.5")){
      secondBody = this.body
    }

    secondBody = "{\"list\":" + secondBody + "}"
    val page2 = this.getValue[ListHolder](secondBody, null)

    println(page2.list.size)
    println(page2)
    assert(5 == page2.list.size)
    assert(page2.list(0).firstName == "11")
    assert(page2.list(4).firstName == "15")


    this.get("/authors?itemsInList=100", Seq.empty, Map("accept" -> "application/json,;q=0.9,text/plain;q=0.8,image/png,*//*;q=0.5")){
      secondBody = this.body
    }

    secondBody = "{\"list\":" + secondBody + "}"
    val allPages = this.getValue[ListHolder](secondBody, null)

    assert(15 == allPages.list.size)
    assert(allPages.list(0).firstName == "1")
    assert(allPages.list(14).firstName == "15")

    startTx
    transaction{
      allPages.list.foreach(f => dao.delete(f))
    }
    commitTx

  }

  test("get /:id"){
    val controller = new CrudController[Author, Long](dao, "authors")

    startTx
    var id: Long = 1
    transaction{
      val author = new Author(0, "1","Doe", Some("johndoe@gmail.com"))
      dao.create(author)
      id = author.id
    }

    commit
    var someBody: String = null
    this.get("/authors/" + id, Seq.empty, Map("accept" -> "application/json,;q=0.9,text/plain;q=0.8,image/png,*//*;q=0.5")){
      someBody = this.body
    }

    val auth = this.getValue[Author](someBody, null)

    assert(auth.id == id)
    assert(auth.firstName == "1")
    assert(auth.lastName == "Doe")

    startTx
    transaction{
      dao.delete(auth)
    }

    commitTx

  }


  test("get /:id/edit"){
    val controller = new CrudController[Author, Long](dao, "authors")

    startTx
    var id: Long = 1
    transaction{
      val author = new Author(0, "1","Doe", Some("johndoe@gmail.com"))
      dao.create(author)
      id = author.id
    }

    commit
    var someBody: String = null
    this.get("/authors/" + id + "/edit", Seq.empty, Map("accept" -> "application/json,;q=0.9,text/plain;q=0.8,image/png,*//*;q=0.5")){
      someBody = this.body
    }

    val auth = this.getValue[Author](someBody, null)

    assert(auth.id == id)
    assert(auth.firstName == "1")
    assert(auth.lastName == "Doe")

    startTx
    transaction{
      dao.delete(auth)
    }

    commitTx

  }
}

case class ListHolder(list: List[Author])


