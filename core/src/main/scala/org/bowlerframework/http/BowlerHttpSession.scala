package org.bowlerframework.http

import javax.servlet.http.HttpSession
import collection.mutable.MutableList
import org.bowlerframework.Session


class BowlerHttpSession(session: HttpSession) extends Session {
  def getId = session.getId

  private val errors = "_bowlerValidationErrors"
  private val lastGetName = "_bowlerLastGetUrl"
  private val validationModel = "_bowlerValidationModel"

  def getAttributeNames: List[String] = {
    val list = new MutableList[String]
    val enum = session.getAttributeNames
    while (enum.hasMoreElements)
      list += enum.nextElement.toString
    return list.toList
  }

  def getCreationTime = session.getCreationTime

  def invalidate = {
    session.invalidate
  }

  def removeAttribute(name: String) = session.removeAttribute(name)

  def setAttribute[T](name: String, value: T) = session.setAttribute(name, value)

  def getAttribute[T](name: String): Option[T] = {
    if (session.getAttribute(name) != null)
      return Some(session.getAttribute(name).asInstanceOf[T])
    else
      return None
  }

  def getUnderlyingSession = session

  def setLastGetPath(path: String) = {
    session.setAttribute(lastGetName, path)
  }

  def getLastGetPath: Option[String] = {
    try {
      val lastGet = session.getAttribute(lastGetName).asInstanceOf[String]
      if (lastGet == null || lastGet == None) {
        return None
      } else
        return Some(lastGet)
    } catch {
      case e: NoSuchElementException => return None
    }
  }

  def resetValidations = {
    session.removeAttribute(errors)
    session.removeAttribute(validationModel)
  }

  def getErrors: Option[List[Tuple2[String, String]]] = {
    try {
      val validationErrors = session.getAttribute(errors).asInstanceOf[List[Tuple2[String, String]]]
      if (validationErrors == null || validationErrors == None) {
        return None
      } else
        return Some(validationErrors)
    } catch {
      case e: NoSuchElementException => return None
    }
  }

  def setErrors(errors: List[(String, String)]) = {
    session.setAttribute(this.errors, errors)
  }

  def getValidatedModel: Option[Seq[Any]] = {
    try {
      val models = session.getAttribute(validationModel).asInstanceOf[Seq[Any]]
      if (models == null || models == None) {
        return None
      } else
        return Some(models)
    } catch {
      case e: NoSuchElementException => return None
    }
  }

  def setValidationModel(model: Seq[Any]) = {
    session.setAttribute(validationModel, model)
  }
}