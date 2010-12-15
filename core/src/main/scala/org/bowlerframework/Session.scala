package org.bowlerframework

/**
 * Created by IntelliJ IDEA.
 * User: wfaler
 * Date: 13/12/2010
 * Time: 00:06
 * To change this template use File | Settings | File Templates.
 */

trait Session{
  def getAttribute[T](name: String): Option[T]
  def setAttribute[T](name: String, value: T)
  def removeAttribute(name: String)
  def invalidate
  def getCreationTime: Long
  def getAttributeNames: List[String]
  def getId: String
}