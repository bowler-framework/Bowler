package org.bowlerframework.model

import com.recursivity.commons.validator.Validator
import collection.mutable.HashMap

/**
 * Initializes itself and its validators with a bean.
 */

trait ModelValidatorBuilder[T]{
  /**
   * Should initialize all Validators with the use of the bean.
   */
  def initialize(bean: T): ModelValidator
}

/**
 * Holds default validations for beans that have registered ModelValidatorBuilders with the registry.
 */
object ModelValidatorBuilder{
  private val registry = new HashMap[Class[_], ModelValidatorBuilder[_]]

  def registerValidatorBuilder(cls: Class[_], validator: ModelValidatorBuilder[_]) = registry.put(cls, validator)

  def apply(cls: Class[_]): Option[ModelValidatorBuilder[_]] = {
    try{
      return Some(registry(cls))
    }catch{
      case e: NoSuchElementException => return None
    }
  }
}