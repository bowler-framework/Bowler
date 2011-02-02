package org.bowlerframework.model

import com.recursivity.commons.validator.Validator

/**
 * Created by IntelliJ IDEA.
 * User: wfaler
 * Date: 01/02/2011
 * Time: 23:57
 * To change this template use File | Settings | File Templates.
 */

trait ModelValidatorBuilder[T] extends ModelValidator{
  def initialize(bean: T)
}