package org.bowlerframework.view.scalate.selectors

import org.bowlerframework.Request


class DefaultSelector[T](item: T) extends RequestSelector[T] {
  def find(request: Request) = Some(item)
}