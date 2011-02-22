package org.bowlerframework.examples.jpa

import org.bowlerframework.model.{DefaultModelValidator, ModelValidator, ModelValidatorBuilder}
import com.recursivity.commons.validator.MinLength

/**
 * Created by IntelliJ IDEA.
 * User: wfaler
 * Date: 22/02/2011
 * Time: 20:44
 * To change this template use File | Settings | File Templates.
 */

class CarValidatorBuilder extends ModelValidatorBuilder[Car]{
  def initialize(bean: Car): ModelValidator = {
    val builder = new DefaultModelValidator(classOf[Car])
    builder.add(MinLength("model", 3, {bean.model}))
    return builder
  }
}