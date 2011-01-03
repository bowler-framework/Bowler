package org.bowlerframework.view.scalate

import util.matching.Regex
import org.bowlerframework.{Request, HTTP}

/**
 * Created by IntelliJ IDEA.
 * User: wfaler
 * Date: 03/01/2011
 * Time: 22:27
 * To change this template use File | Settings | File Templates.
 */

object TemplateRegistry{

  def registerTemplateSuffix(userAgent: String, suffix: String){registerTemplateSuffix(Map("User-Agent" -> userAgent), suffix)}
  def registerLayout(userAgent: String, layout: Layout){registerLayout(Map("User-Agent" -> userAgent), layout)}
  def registerLayout(method: HTTP.Method, path: Regex, layout: Layout){registerLayout(method, path.toString + ":regex", layout)}
  def registerLayout(method: HTTP.Method, path: String, userAgent: String, layout: Layout){registerLayout(method, path, Map("User-Agent" -> userAgent), layout)}
  def registerLayout(method: HTTP.Method, path: Regex, userAgent: String, layout: Layout){registerLayout(method, path.toString + ":regex", Map("User-Agent" -> userAgent), layout)}
  def registerLayout(method: HTTP.Method, path: Regex, headerSelectors: Map[String, String], layout: Layout){registerLayout(method, path.toString + ":regex", headerSelectors, layout)}

  def registerLayout(method: HTTP.Method, path: String, layout: Layout){registerLayout(method, path, Map[String, String](), layout)}

  // TODO implement these
  def registerTemplateSuffix(headerSelectors: Map[String, String], suffix: String){}
  def registerBaseLayout(layout: Layout){}
  def registerLayout(headerSelectors: Map[String, String], layout: Layout){}
  def registerLayout(method: HTTP.Method, path: String, headerSelectors: Map[String, String], layout: Layout){}
  def resolveLayout(request: Request) = None
}