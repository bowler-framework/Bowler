package org.bowlerframework

/**
 * Base session abstraction for Bowler
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
  def resetValidations

  def getLastGetPath: Option[String]
  def setLastGetPath(path: String)

  def setValidationModel(model: Seq[Any])
  def getValidatedModel: Option[Seq[Any]]

}