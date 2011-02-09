package org.bowlerframework.squeryl

import dao.LongKeyedDao
import org.scalatra.test.scalatest.ScalatraFunSuite
import org.squeryl.PrimitiveTypeMode._
import org.bowlerframework.http.BowlerFilter
import com.recursivity.commons.validator.MinLength
import org.bowlerframework.model.{ModelValidator, DefaultModelValidator, ModelValidatorBuilder}

/**
 * Created by IntelliJ IDEA.
 * User: wfaler
 * Date: 01/02/2011
 * Time: 01:05
 * To change this template use File | Settings | File Templates.
 */

class CrudControllerTest extends ScalatraFunSuite with InMemoryDbTest{
    import Library._

  ModelValidatorBuilder.registerValidatorBuilder(classOf[Author], new AuthorValidatorBuilder)

  val dao = new LongKeyedDao[Author](authors)
  val holder = this.addFilter(classOf[BowlerFilter], "/*")

  test("get /"){
    val controller = new CrudController[Author, Long](new SquerylController,dao, "authors")

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
    val controller = new CrudController[Author, Long](new SquerylController,dao, "authors")

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
    val controller = new CrudController[Author, Long](new SquerylController,dao, "authors")

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


  test("create"){
    val controller = new CrudController[Author, Long](new SquerylController,dao, "authors")
    var result: String = null
    val list = List[Tuple2[String, String]](("author.id", "0"), ("author.firstName", "postAuthor"), ("author.lastName", "author"), ("author.email", "some@email.com"))

    this.post("/authors/", list, Map("accept" -> "application/json,;q=0.9,text/plain;q=0.8,image/png,*//*;q=0.5")){
      result = this.body
    }

    var auth = this.getValue[Author](result, null)

    assert(auth.id > 0)
    assert(auth.firstName == "postAuthor")
    assert(auth.lastName == "author")
    assert(auth.email.get == "some@email.com")

    this.get("/authors/" + auth.id,  Seq.empty, Map("accept" -> "application/json,;q=0.9,text/plain;q=0.8,image/png,*//*;q=0.5")){
      result = this.body
    }

    auth = this.getValue[Author](result, null)

    assert(auth.id > 0)
    assert(auth.firstName == "postAuthor")
    assert(auth.lastName == "author")
    assert(auth.email.get == "some@email.com")

    startTx
    transaction{
      dao.delete(auth)
    }

    commitTx

  }

  test("create with unique fail"){
    startTx
    var id: Long = 1
    transaction{
      val author = new Author(0, "4qweqe5","Doe", Some("johndoe@gmail.com"))
      dao.create(author)
      id = author.id
    }
    commit

    val controller = new CrudController[Author, Long](new SquerylController,dao, "authors")
    var result: Int = 200
    val list = List[Tuple2[String, String]](("author.id", "" + id), ("author.firstName", "postAuthor"), ("author.lastName", "author"), ("author.email", "some@email.com"))

    this.post("/authors/", list, Map("accept" -> "application/json,;q=0.9,text/plain;q=0.8,image/png,*//*;q=0.5")){
      result = this.status
    }

    println("RESULT: " + result)
    assert(400 == result)

    startTx
    transaction{
      dao.delete(dao.findById(id).get)
    }

    commitTx

  }

  test("create with ModelValidatorBuilder (name to short)"){
    val controller = new CrudController[Author, Long](new SquerylController,dao, "authors")
    var result: Int = 200
    val list = List[Tuple2[String, String]](("author.id", "0"), ("author.firstName", "w"), ("author.lastName", "author"), ("author.email", "some@email.com"))

    this.post("/authors/", list, Map("accept" -> "application/json,;q=0.9,text/plain;q=0.8,image/png,*//*;q=0.5")){
      result = this.status
      println(this.body)
    }

    assert(400 == result)

  }

  test("update"){
    startTx
    var id: Long = 1
    transaction{
      val author = new Author(0, "45","Doe", Some("johndoe@gmail.com"))
      dao.create(author)
      id = author.id
    }
    commit


    val controller = new CrudController[Author, Long](new SquerylController,dao, "authors")
    var result: Int = 200

    println("AUTHOR ID: " +  id)
    val list = List[Tuple2[String, String]](("author.id", "" + id), ("author.firstName", "postAuthor"), ("author.lastName", "author"), ("author.email", "some@email.com"))

    this.post("/authors/" + id, list, Map("accept" -> "application/json,;q=0.9,text/plain;q=0.8,image/png,*//*;q=0.5")){
      result = this.status
    }

    var someBody: String = null
    this.get("/authors/" + id, Seq.empty, Map("accept" -> "application/json,;q=0.9,text/plain;q=0.8,image/png,*//*;q=0.5")){
      someBody = this.body
    }

    val auth = this.getValue[Author](someBody, null)


    assert(auth.id == id)
    assert(auth.firstName == "postAuthor")
    assert(auth.lastName == "author")
    assert(auth.email.get == "some@email.com")

    startTx
    transaction{
      dao.delete(auth)
    }

    commitTx

  }

  test("update with id fail"){
    startTx
    var id: Long = 1
    transaction{
      val author = new Author(0, "45","Doe", Some("johndoe@gmail.com"))
      dao.create(author)
      id = author.id
    }
    commit


    val controller = new CrudController[Author, Long](new SquerylController,dao, "authors")
    var result: Int = 200

    println("AUTHOR ID: " +  id)
    val list = List[Tuple2[String, String]](("author.id", "" + (id + 1)), ("author.firstName", "postAuthor"), ("author.lastName", "author"), ("author.email", "some@email.com"))

    this.post("/authors/" + id, list, Map("accept" -> "application/json,;q=0.9,text/plain;q=0.8,image/png,*//*;q=0.5")){
      result = this.status
    }

    assert(400 == result)

    startTx
    transaction{
      dao.delete(dao.findById(id).get)
    }

    commitTx
  }

}

case class ListHolder(list: List[Author])

class AuthorValidatorBuilder extends DefaultModelValidator(classOf[Author]) with ModelValidatorBuilder[Author]{

  def initialize(author: Author): ModelValidator = {
    val validator: ModelValidator = new DefaultModelValidator(classOf[Author])
    validator.add(MinLength("firstName", 3, {author.firstName}))
    return validator
  }
}


