package org.bowlerframework.view.scalate

import reflect.BeanProperty
import org.bowlerframework.{RequestScope, Request}
import collection.mutable.HashMap

/**
 * Retrieves a Template based on a request and it's contents, headers and/or path.
 * the order of layouts and templates added matters, as the selectors are held in order and the first match will return a result. <br/>
 * Also holds Scalate configurations, such as TemplateResolver, preference order of Scalate template types (ssp, jade, mustache etc), root packages/folders for views and layouts etc.
 */
object TemplateRegistry {

  var templateTypePreference = List(".mustache", ".ssp", ".jade", ".scaml")

  var templateResolver: TemplateResolver = new ClasspathTemplateResolver

  var rootViewPackageOrFolder = "/views"

  var rootLayoutPackageOrFolder = "/layouts"

  val controllerLayouts =  new HashMap[Class[_], Layout]

  var defaultLayout: Function1[Request, Option[Layout]] = {(request) => None}

  var suffixResolver: Function1[Request, List[String]] = {(request) => List[String]()}

  def getSuffixes(request: Request): List[String] = suffixResolver(request)
}