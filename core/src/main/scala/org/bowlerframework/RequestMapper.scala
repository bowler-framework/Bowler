package org.bowlerframework

/**
 * Created by IntelliJ IDEA.
 * User: wfaler
 * Date: 21/12/2010
 * Time: 21:55
 * To change this template use File | Settings | File Templates.
 */

trait RequestMapper{
  def getValue[T](request: Request, nameHint: String = null)(implicit m: Manifest[T]): T
}