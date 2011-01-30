package org.bowlerframework.squeryl


import dao.LongKeyedDao
import org.scalatest.FunSuite
import org.squeryl.PrimitiveTypeMode._



/**
 * Created by IntelliJ IDEA.
 * User: wfaler
 * Date: Oct 28, 2010
 * Time: 11:21:43 PM
 * To change this template use File | Settings | File Templates.
 */

class SquerylDaoTest extends FunSuite with InMemoryTest{
  import Library._

  val dao = new LongKeyedDao[Author](authors)

  test("CRUD"){
    startTx

    transaction{
      val author = new Author(0, "John","Doe", Some("johndoe@gmail.com"))

      dao.create(author)
      val id = author.id

      val auth = dao.findById(id)
      assert(auth != None)
      assert(auth.get.id == id)
      assert(auth.get.firstName == "John")
      assert(auth.get.lastName == "Doe")
      assert(auth.get.email.get == "johndoe@gmail.com")

      val update = Author(id, "Wille", "Faler", None)
      dao.update(update)

      val updated = dao.findById(id)


      assert(updated != None)
      assert(updated.get.id == id)
      assert(updated.get.firstName == "Wille")
      assert(updated.get.lastName == "Faler")
      assert(updated.get.email == None)

      dao.delete(author)

      assert(dao.findById(id) == None)

    }
    commitTx
  }

  test("findAll"){
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
      assert(all.head.lastName == "Doe")

      val firstTen = dao.findAll(0, 10)
      println(firstTen.size)
      assert(firstTen.size == 10)


    }

    commitTx

  }


}

