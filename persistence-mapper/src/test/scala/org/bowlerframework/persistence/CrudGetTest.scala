package org.bowlerframework.persistence

import org.scalatra.test.scalatest.ScalatraFunSuite
import org.squeryl.PrimitiveTypeMode._
import org.bowlerframework.http.BowlerFilter
import com.recursivity.commons.validator.MinLength
import org.bowlerframework.model.{ModelValidator, DefaultModelValidator, ModelValidatorBuilder}

/**
 * Created by IntelliJ IDEA.
 * User: wfaler
 * Date: 20/02/2011
 * Time: 20:37
 * To change this template use File | Settings | File Templates.
 */

class CrudGetTest  extends ScalatraFunSuite with InMemoryDbTest{
    import Library._

  ModelValidatorBuilder.registerValidatorBuilder(classOf[Author], new AuthorValidatorBuilder)

  val dao = new LongKeyedDao[Author](authors)
  val holder = this.addFilter(classOf[BowlerFilter], "/*")

  startTx
  commit


  test("get /new"){
    val controller = new CrudController[Author, Long](new SquerylController,dao, "authors")
    var someBody: String = null
    this.get("/authors/new"){
      someBody = this.body
    }
    assert("NEW" == someBody)

  }


  test("get /id"){
    val controller = new CrudController[Author, Long](new SquerylController,dao, "authors")
    var someBody: String = null
    this.get("/authors/1"){
      someBody = this.body
    }
    assert("ID" == someBody)

  }


  test("get /edit"){
    val controller = new CrudController[Author, Long](new SquerylController,dao, "authors")
    var someBody: String = null
    this.get("/authors/1/edit"){
      someBody = this.body
    }
    assert("EDIT" == someBody)

  }
}