package org.bowlerframework.model

import com.recursivity.commons.validator.Validator

/**
 * convenience trait for implementing validators inside a validate{} block
 */

trait ModelValidator {
  def validate: Option[List[Tuple2[String, String]]]

  def add(validator: Validator)
}