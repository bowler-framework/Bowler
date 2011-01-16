package org.bowlerframework.model

import com.recursivity.commons.validator.{ClasspathMessageResolver, ValidationGroup}

/**
 * Default helper class for dealing with validations (built on the validations with localisation support in <a href="http://github.com/wfaler/recursivity-commons">Recursivity Commons</a>
 */

class DefaultModelValidator(messageBundleContext: Class[_]) extends ValidationGroup(new ClasspathMessageResolver(messageBundleContext)) with ModelValidator{
  def validate: Option[List[Tuple2[String, String]]] = {
    val failures = validateAndReturnErrorMessages
    if (failures != null && failures.size > 0)
      Some(failures)
    else None
  }
}