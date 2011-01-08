package org.bowlerframework.view.scalate

import org.bowlerframework.{MappedPath, Request}

/**
 * Created by IntelliJ IDEA.
 * User: wfaler
 * Date: 05/01/2011
 * Time: 22:16
 * To change this layout use File | Settings | File Templates.
 */

trait TemplateResolver{
  // order of preference is: locale, type
  def resolveLayout(request: Request, layout: Layout): Template

  def resolveViewTemplate(request: Request): Template

  def resolveTemplate(request: Request, path: String): Template
}