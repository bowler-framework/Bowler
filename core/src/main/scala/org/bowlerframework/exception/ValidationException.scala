package org.bowlerframework.exception

/**
 * Depicts a validation error - should be handled in a client appropriate way to give feedback on what validation failed,
 * the validation failures kan be retrieved in property -> message form from the errors list.<br/>
 * Associated with a HTTP 400 code for clients that should return an HTTP Code instead of/with visual feedback
 */

class ValidationException(val errors: List[Tuple2[String, String]]) extends HttpException(400)