package org.bowlerframework.exception

/**
 * AccessDeniedException should be thrown if a user does not have access to a Resource or needs to login.<br/>
 * Analogous to a HTTP 403 code.
 */

class AccessDeniedException extends HttpException(403)