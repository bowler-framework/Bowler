package org.bowlerframework.view.scalate.selectors

import org.bowlerframework.Request

/**
 * Created by IntelliJ IDEA.
 * User: wfaler
 * Date: 07/01/2011
 * Time: 18:26
 * To change this template use File | Settings | File Templates.
 */

trait RequestSelector[T]{
  def find(request: Request): Option[T]
}