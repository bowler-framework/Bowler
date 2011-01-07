package org.bowlerframework.view.scalate.selectors

import org.bowlerframework.Request

/**
 * Created by IntelliJ IDEA.
 * User: wfaler
 * Date: 07/01/2011
 * Time: 18:28
 * To change this template use File | Settings | File Templates.
 */

class DefaultSelector[T](item: T) extends RequestSelector[T]{
  def find(request: Request) = Some(item)
}