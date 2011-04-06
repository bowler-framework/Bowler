package org.bowlerframework.view.scalate.selectors

import util.matching.Regex
import org.bowlerframework.Request


class HeaderSelector[T](item: T, headerSelectors: Map[String, Regex]) extends RequestSelector[T] {

  def find(request: Request): Option[T] = {
    var conditionsMet = true
    try {
      headerSelectors.iterator.foreach(tup => {
        if (!tup._2.pattern.matcher(request.getHeader(tup._1)).matches)
          conditionsMet = false
      })
    } catch {
      case e: NoSuchElementException => {
        conditionsMet = false
      }
    }

    if (!conditionsMet) return None
    else return Some(item)
  }
}