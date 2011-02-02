package org.bowlerframework.model

import collection.mutable.HashMap

/**
 * Holds default validations for beans that have registered ModelValidatorBuilders with the registry.
 */

object DefaultValidationRegistry{
  private val registry = new HashMap[Class[_], ModelValidatorBuilder[_]]

  def registerValidatorBuilder(cls: Class[_], validator: ModelValidatorBuilder[_]) = registry.put(cls, validator)

  def getValidatorBuilder(cls: Class[_]): Option[ModelValidatorBuilder[_]] = {
    try{
      return Some(registry(cls))
    }catch{
      case e: NoSuchElementException => return None
    }
  }
}