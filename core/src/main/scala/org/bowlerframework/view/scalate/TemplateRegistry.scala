package org.bowlerframework.view.scalate

import reflect.BeanProperty
import org.bowlerframework.{RequestScope, Request}

/**
 * Retrieves a Template based on a request and it's contents, headers and/or path.
 * the order of layouts and templates added matters, as the selectors are held in order and the first match will return a result. <br/>
 * Also holds Scalate configurations, such as TemplateResolver, preference order of Scalate template types (ssp, jade, mustache etc), root packages/folders for views and layouts etc.
 */
object TemplateRegistry {

  @BeanProperty
  var templateTypePreference = List(".mustache", ".ssp", ".jade", ".scaml")

  @BeanProperty
  var templateResolver: TemplateResolver = new ClasspathTemplateResolver

  @BeanProperty
  var rootViewPackageOrFolder = "/views"

  @BeanProperty
  var rootLayoutPackageOrFolder = "/layouts"

  var layoutResolver: Function1[Request, Option[Layout]] = {(request) => None}

  var suffixResolver: Function1[Request, List[String]] = {(request) => List[String]()}

  def getLayout(request: Request): Option[Layout] = layoutResolver(request)

  def getSuffixes(request: Request): List[String] = suffixResolver(request)
}