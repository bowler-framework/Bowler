package org.bowlerframework.model

/**
 * Created by IntelliJ IDEA.
 * User: wfaler
 * Date: 15/12/2010
 * Time: 21:57
 * To change this template use File | Settings | File Templates.
 */

trait Validations{

  /**
   * Runs a function that does validations - errors are designated by either:<br/>
   * - having the closure/passed function return None                         <br/>
   * - Or having the Some(List[Tuple2[String,String]]) have an empty list<br/>
   * Validation failure runs the onValidationErrors function and terminates processing of the request. <br/>
   * List should be in the format of key -> message, ie key of property that failed validation and message to be shown to client.
   */
  def validate(function: => Option[List[Tuple2[String, String]]]){
    val errors = function
    if(!None.equals(errors) && errors.get.size > 0){
      onValidationErrors(errors.get)
      throw new ValidationException(errors.get)
    }
  }

  /**
   * If validations fail, this function is called
   */
  def onValidationErrors(errors: List[Tuple2[String, String]])
}