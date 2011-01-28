package org.bowlerframework.squeryl


import org.scalatest.FunSuite
import org.squeryl.{SessionFactory, Session}
import org.squeryl.PrimitiveTypeMode._
import java.io.{PrintWriter, StringWriter}
import org.squeryl.adapters.{MySQLAdapter, H2Adapter}
import org.squeryl.internals.DatabaseAdapter
import com.mchange.v2.c3p0.{ComboPooledDataSource}

/**
 * Created by IntelliJ IDEA.
 * User: wfaler
 * Date: Oct 28, 2010
 * Time: 11:21:43 PM
 * To change this template use File | Settings | File Templates.
 */

class SquerylTest extends FunSuite{

  val adapter: Map[String, DatabaseAdapter] = Map(
		"h2" -> new H2Adapter,
		"mysql" -> new MySQLAdapter
	)

	val driver: Map[String,String] = Map(
		"h2" -> "org.h2.Driver",
		"mysql" -> "com.mysql.jdbc.Driver"
	)

	val cpds  = new ComboPooledDataSource
	cpds.setDriverClass(driver("h2"))
	cpds.setJdbcUrl("jdbc:h2:test")
	cpds.setUser("sa")
	cpds.setPassword("")

	cpds.setMinPoolSize(1)
	cpds.setAcquireIncrement(1)
	cpds.setMaxPoolSize(1)

	def connection = {
		Session.create(cpds.getConnection, adapter("h2"))
	}

	SessionFactory.concreteFactory = Some(() => connection)



  test("Stuff "){
    val session = SessionFactory.newSession
    session.bindToCurrentThread

    val writer = new StringWriter
    Library.printDdl(new PrintWriter(writer))
    println(writer.toString)


    //val con = session.connection
   // con.createStatement.execute(writer.toString)

   // Library.authors.insert(new Author(1, "Michel","Folco", Some("michel@folco.com")))
    transaction{
      val a = from(Library.authors)(a=> where(a.lastName === "Folco") select(a))
      println("Author is: " + a.head.email)
    }


   session.close
   cpds.close

  }


}

import org.squeryl.Schema

/**
 * Created by IntelliJ IDEA.
 * User: wfaler
 * Date: Oct 28, 2010
 * Time: 11:44:35 PM
 * To change this template use File | Settings | File Templates.
 */

object Library extends Schema {

  val authors = table[Author]("AUTHORS")
}

case class Author(val id: Long,
             val firstName: String,
             val lastName: String,
             val email: Option[String]) {

  def this() = this(0,"","",Some(""))
}
