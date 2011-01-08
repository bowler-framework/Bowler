package org.bowlerframework.view.scalate

import selectors.{TemplateSuffixSelector, LayoutSelector}
import util.matching.Regex
import org.bowlerframework.{Request, HTTP}
import collection.mutable.{MutableList, HashMap}
import reflect.BeanProperty

/**
 * Retrieves a Template based on a request and it's contents, headers and/or path.
 * the order of layouts and templates added matters, as the selectors are held in order and the first match will return a result.
 */

object TemplateRegistry{

  @BeanProperty
  var templateTypePreference = List(".mustache", ".ssp", ".jade", ".scaml")

  @BeanProperty
  var templateResolver: TemplateResolver = new ClasspathTemplateResolver

  @BeanProperty
  var rootViewPackageOrFolder = "/views"

  @BeanProperty
  var rootLayoutPackageOrFolder = "/layouts"

  private var suffixSelectors = new MutableList[TemplateSuffixSelector]()

  private var layoutSelectors = new MutableList[LayoutSelector]()

  def appendTemplateSelectors(selectors: List[LayoutSelector]) = selectors.foreach(f => {layoutSelectors += f})

  def appendTemplateSelector(selector: LayoutSelector) = {layoutSelectors += selector}

  def appendSuffixSelectors(selectors: List[TemplateSuffixSelector]) = selectors.foreach(f => {suffixSelectors += f})

  def appendSuffixSelector(selector: TemplateSuffixSelector) = {suffixSelectors += selector}

  def reset = {
    layoutSelectors = new MutableList[LayoutSelector]()
    suffixSelectors = new MutableList[TemplateSuffixSelector]()
  }

  def getLayout(request: Request) = layoutSelectors.find(p => {p.find(request) != None}).get.find(request).get

  def getSuffixes(request: Request): List[String] = suffixSelectors.filter(p => {p.find(request) != None}).map(f => {f.find(request).get}).toList


  // order of preference is:
  // suffix, locale, type

  // order of preference is: locale, type

}