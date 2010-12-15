package org.bowlerframework.controller

/**
 * Created by IntelliJ IDEA.
 * User: wfaler
 * Date: 15/12/2010
 * Time: 21:57
 * To change this template use File | Settings | File Templates.
 */

trait Validations{

  def validate(function: => Option[List[Tuple2[String, String]]]){
    val errors = function
    if(!None.equals(errors)){
      onValidationErrors(errors.get)
      throw new RequestValidationException(errors.get)
    }
  }

  def onValidationErrors(errors: List[Tuple2[String, String]])
}