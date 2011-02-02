package org.bowlerframework.model

import com.recursivity.commons.validator.Validator

/**
 * Initializes itself and its validators with a bean.
 */

trait ModelValidatorBuilder[T] extends ModelValidator{
  /**
   * Should initialize all Validators with the use of the bean.
   */
  def initialize(bean: T)
}