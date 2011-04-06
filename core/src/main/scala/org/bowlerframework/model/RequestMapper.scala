package org.bowlerframework.model

import org.bowlerframework.Request

/**
 * Maps a single value from a request, for instance mapping a bean from a request.
 */

trait RequestMapper {
  def getValue[T](request: Request, nameHint: String = null)(implicit m: Manifest[T]): T
}