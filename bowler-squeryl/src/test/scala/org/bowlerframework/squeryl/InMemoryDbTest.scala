package org.bowlerframework.squeryl
import org.squeryl.PrimitiveTypeMode._
import java.io.{PrintWriter, StringWriter}
import org.squeryl.adapters.{MySQLAdapter, H2Adapter}
import org.squeryl.internals.DatabaseAdapter
import com.mchange.v2.c3p0.{ComboPooledDataSource}
import org.squeryl.dsl.ast.{ConstantExpressionNode, BinaryOperatorNodeLogicalBoolean}
import org.squeryl.{KeyedEntity, SessionFactory, Session}
/**
 * Created by IntelliJ IDEA.
 * User: wfaler
 * Date: 30/01/2011
 * Time: 17:33
 * To change this template use File | Settings | File Templates.
 */

trait InMemoryDbTest{
  var cpds  = new ComboPooledDataSource
  var session: Session = null

  var started = false

  def startTx = {
    var cpds  = new ComboPooledDataSource
    cpds.setDriverClass("org.h2.Driver")
    //cpds.setDriverClass("com.mysql.jdbc.Driver")
    cpds.setJdbcUrl("jdbc:h2:mem:test")
    cpds.setUser("sa")
    cpds.setPassword("")

    cpds.setMinPoolSize(1)
    cpds.setAcquireIncrement(1)
    cpds.setMaxPoolSize(1)

    def connection = {
      Session.create(cpds.getConnection, new H2Adapter)
    }

    SessionFactory.concreteFactory = Some(() => connection)
    session = SessionFactory.newSession
    session.bindToCurrentThread


    try{
      val writer = new StringWriter
      Library.printDdl(new PrintWriter(writer))
      println(writer.toString)
      val con = session.connection
      con.createStatement.execute(writer.toString)
    }catch{
      case e: Exception => {
        println("database exists: " + e)
      }
    }
  }

  def commitTx = {
   session.close
   cpds.close
  }
}


import org.squeryl.Schema

object Library extends Schema {

  val authors = table[Author]("authors")
}

case class Author(val id: Long,
             val firstName: String,
             val lastName: String,
             val email: Option[String]) extends KeyedEntity[Long]{

  def this() = this(0,"","",Some(""))
}