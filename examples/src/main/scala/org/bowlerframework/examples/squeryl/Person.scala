package org.bowlerframework.examples.squeryl

import org.squeryl.KeyedEntity

/**
 * Created by IntelliJ IDEA.
 * User: wfaler
 * Date: 20/02/2011
 * Time: 20:02
 * To change this template use File | Settings | File Templates.
 */

case class Person(val id: Long, var firstName: String, var lastName: String) extends KeyedEntity[Long]{
  def this() = this(0, "","")
}