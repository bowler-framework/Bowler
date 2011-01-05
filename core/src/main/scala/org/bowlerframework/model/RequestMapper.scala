package org.bowlerframework.model

import org.bowlerframework.Request

/**
 * Created by IntelliJ IDEA.
 * User: wfaler
 * Date: 21/12/2010
 * Time: 21:55
 * To change this layout use File | Settings | File Templates.
 */

trait RequestMapper{
  def getValue[T](request: Request, nameHint: String = null)(implicit m: Manifest[T]): T
}