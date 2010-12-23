package org.bowlerframework.model

import org.bowlerframework.Request

/**
 * Created by IntelliJ IDEA.
 * User: wfaler
 * Date: 21/12/2010
 * Time: 22:13
 * To change this template use File | Settings | File Templates.
 */

class JsonRequestMapper extends RequestMapper{
  def getValue[T](request: Request, nameHint: String)(implicit m: Manifest[T]) = None.asInstanceOf[T]
}