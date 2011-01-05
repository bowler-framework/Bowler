package org.bowlerframework.exception

/**
 * Created by IntelliJ IDEA.
 * User: wfaler
 * Date: 15/12/2010
 * Time: 21:55
 * To change this layout use File | Settings | File Templates.
 */

class ValidationException(val errors: List[Tuple2[String, String]]) extends HttpException(400)