package org.bowlerframework.exception

/**
 *  Base Exception type for HTTP Exceptions associated with an HTTP code.
 * Should be extended for Exceptions that have an HTTP Code equivalent.
 */

class HttpException(val code: Int = 500) extends Exception