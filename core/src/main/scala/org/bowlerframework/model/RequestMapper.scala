package org.bowlerframework.model

import org.bowlerframework.Request
import com.recursivity.commons.bean.GenericTypeDefinition

/**
 * Maps a single value from a request, for instance mapping a bean from a request.
 */

trait RequestMapper {
  def getValue[T](request: Request, nameHint: String = null)(implicit m: Manifest[T]): T
  def getValueWithTypeDefinition(typeDefinition: GenericTypeDefinition, request: Request, nameHint: String = null): Any
}