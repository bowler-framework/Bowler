package org.bowlerframework.squeryl
import org.squeryl.PrimitiveTypeMode._
import java.io.{PrintWriter, StringWriter}
import org.squeryl.adapters.{MySQLAdapter, H2Adapter}
import org.squeryl.internals.DatabaseAdapter
import com.mchange.v2.c3p0.{ComboPooledDataSource}
import org.squeryl.dsl.ast.{ConstantExpressionNode, BinaryOperatorNodeLogicalBoolean}
import org.squeryl.{KeyedEntity, SessionFactory, Session}
import net.liftweb.json.JsonParser._
/**
 * Created by IntelliJ IDEA.
 * User: wfaler
 * Date: 30/01/2011
 * Time: 17:33
 * To change this template use File | Settings | File Templates.
 */

trait InMemoryDbTest{
  implicit val formats = net.liftweb.json.DefaultFormats
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
     // println(writer.toString)
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

  def commit = {
    session.close
  }

  def startPage = {
    startTx
    session.close

  }

  def getValue[T](body: String, nameHint: String)(implicit m: Manifest[T]): T = {
    val json = parse(body)
    if (nameHint == null) {
      return json.extract[T]
    } else {
      val namedJson = json \\ nameHint
      return namedJson.extract[T]
    }
  }


}


import org.squeryl.Schema

object Library extends Schema {

  val authors = table[Author]("authors")
  val people = table[Person]("people")
}

case class Person(val id: String,
             val name: String) extends KeyedEntity[String]{

  def this() = this("","")
}

