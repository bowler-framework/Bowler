package org.bowlerframework.model

import collection.mutable.HashMap

/**
 * Created by IntelliJ IDEA.
 * User: wfaler
 * Date: 01/02/2011
 * Time: 23:47
 * To change this template use File | Settings | File Templates.
 */

object DefaultValidationRegistry{
  private val registry = new HashMap[Class[_], ModelValidatorBuilder[_]]

  def addValidation(cls: Class[_], validator: ModelValidatorBuilder[_]) = registry.put(cls, validator)

  def getValidators(cls: Class[_]): Option[ModelValidatorBuilder[_]] = {
    try{
      return Some(registry(cls))
    }catch{
      case e: NoSuchElementException => return None
    }
  }
}