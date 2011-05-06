package org.bowlerframework.extractors

import org.bowlerframework.Request


class HeadersContain[T](item: T, headers: Map[String, String]) {
  def unapply(request: Request): Option[T] = {
    var conditionsMet = true
    headers.iterator.foreach(tup => {
      try {
        if (!request.getHeader(tup._1).getOrElse("").contains(tup._2))
          conditionsMet = false
      } catch {
        case e: NoSuchElementException => {
          conditionsMet = false
        }
      }

    })
    if (!conditionsMet) return None
    else return Some(item)
  }
}