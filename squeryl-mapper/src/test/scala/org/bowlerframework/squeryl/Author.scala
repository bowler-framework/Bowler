package org.bowlerframework.squeryl

import org.squeryl.KeyedEntity

case class Author(val id: Long, val firstName: String, var lastName: String, var email: Option[String]) extends KeyedEntity[Long]{

  def this() = this(0,"","",Some(""))
}