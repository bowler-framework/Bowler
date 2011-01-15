package org.bowlerframework.model

import org.bowlerframework.exception.ValidationException
import org.bowlerframework.RequestScope._

/**
 * Created by IntelliJ IDEA.
 * User: wfaler
 * Date: 15/12/2010
 * Time: 21:57
 * To change this layout use File | Settings | File Templates.
 */

trait Validations{

  /**
   * Runs a function that does validations - errors are designated by either:<br/>
   * - having the closure/passed function return None                         <br/>
   * - Or having the Some(List[Tuple2[String,String]]) have an empty list<br/>
   * Validation failure runs the onValidationErrors function and terminates processing of the request. <br/>
   * List should be in the format of key -> message, ie key of property that failed validation and message to be shown to client. <br/><br/>
   *
   */
  def validate(toValidate: Any*)(function: => Option[List[Tuple2[String, String]]]){
    request.getSession.resetValidations
    val errors = function
    if(!None.equals(errors) && errors.get.size > 0){
      request.getSession.setValidationModel(toValidate.toSeq)
      throw new ValidationException(errors.get)
    }
  }
}
