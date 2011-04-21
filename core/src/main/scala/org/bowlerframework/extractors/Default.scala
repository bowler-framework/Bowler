package org.bowlerframework.extractors

import org.bowlerframework.Request

/**
 * Created by IntelliJ IDEA.
 * User: wfaler
 * Date: 20/04/2011
 * Time: 23:17
 * To change this template use File | Settings | File Templates.
 */

class Default[T](value: T){
  def unapply(request: Request): Option[T] = Option(value)
}