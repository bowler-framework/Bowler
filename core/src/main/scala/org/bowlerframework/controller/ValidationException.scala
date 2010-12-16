package org.bowlerframework.controller

/**
 * Created by IntelliJ IDEA.
 * User: wfaler
 * Date: 15/12/2010
 * Time: 21:55
 * To change this template use File | Settings | File Templates.
 */

class ValidationException(errors: List[Tuple2[String, String]]) extends Exception{

}