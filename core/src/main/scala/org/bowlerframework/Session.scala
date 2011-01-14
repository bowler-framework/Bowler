package org.bowlerframework

/**
 * Created by IntelliJ IDEA.
 * User: wfaler
 * Date: 13/12/2010
 * Time: 00:06
 * To change this layout use File | Settings | File Templates.
 */

trait Session{
  def getAttribute[T](name: String): Option[T]
  def setAttribute[T](name: String, value: T)
  def removeAttribute(name: String)
  def invalidate
  def getCreationTime: Long
  def getAttributeNames: List[String]
  def getId: String

  def setErrors(errors: List[Tuple2[String, String]])
  def getErrors: Option[List[Tuple2[String, String]]]
  def removeErrors

  def getLastGetPath: Option[String]
  def setLastGetPath(path: String)
}