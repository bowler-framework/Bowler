package org.bowlerframework.view.scalate.selectors

import org.bowlerframework.Request



trait RequestSelector[T]{
  def find(request: Request): Option[T]
}