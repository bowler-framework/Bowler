package org.bowlerframework.extractors

import util.matching.Regex
import org.bowlerframework.Request


class HeadersMatch[T](item: T, headerSelectors: Map[String, Regex]) {

  def unapply(request: Request): Option[T] = {
    var conditionsMet = true
    try {
      headerSelectors.iterator.foreach(tup => {
        if (!tup._2.pattern.matcher(request.getHeader(tup._1).getOrElse("")).matches)
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