package org.bowlerframework.model

/**
 * convenience trait for implementing validators inside a validate{} block
 */

trait ModelValidator{
  def validate: Option[List[Tuple2[String, String]]]
}