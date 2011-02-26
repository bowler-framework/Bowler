package org.bowlerframework.persistence
import org.squeryl.KeyedEntity

case class Author(val id: Long, var firstName: String, var lastName: String, var email: Option[String]) extends KeyedEntity[Long]{
  def this() = this(0,"","",Some(""))
}

case class Author2(id: Long, firstName: String, lastName: String, email: Option[String]){
  def this() = this(0,"","",Some(""))
}