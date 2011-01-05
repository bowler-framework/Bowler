package org.bowlerframework.view.scalate

import selectors.LayoutSelector
import util.matching.Regex
import org.bowlerframework.{Request, HTTP}
import collection.mutable.{MutableList, HashMap}

/**
 * Retrieves a Template based on a request and it's contents, headers and/or path.
 * the order of layouts and templates added matters, as the selectors are held in order and the first match will return a result.
 */

object TemplateRegistry{

  private var layoutSelectors = new MutableList[LayoutSelector]()

  def appendSelectors(selectors: List[LayoutSelector]) = selectors.foreach(f => {layoutSelectors += f})

  def appendSelector(selector: LayoutSelector) = {layoutSelectors += selector}

  def emptyLayoutSelectors = {layoutSelectors = new MutableList[LayoutSelector]()}

  def getLayout(request: Request) = layoutSelectors.find(p => {p.layout(request) != None}).get.layout(request).get
}