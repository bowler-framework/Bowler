package org.bowlerframework.model

import com.recursivity.commons.validator.{ClasspathMessageResolver, ValidationGroup}

/**
 * Created by IntelliJ IDEA.
 * User: wfaler
 * Date: 15/01/2011
 * Time: 23:08
 * To change this template use File | Settings | File Templates.
 */

class DefaultModelValidator(messageBundleContext: Class[_]) extends ValidationGroup(new ClasspathMessageResolver(messageBundleContext)) with ModelValidator{
  def validate: Option[List[Tuple2[String, String]]] = {
    val failures = validateAndReturnErrorMessages
    if (failures != null && failures.size > 0)
      Some(failures)
    else None
  }
}