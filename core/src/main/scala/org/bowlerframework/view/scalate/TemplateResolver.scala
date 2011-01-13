package org.bowlerframework.view.scalate

import org.bowlerframework.{MappedPath, Request}

/**
 * Resolves Templates, View Templates and Layouts for a given request
 */

trait TemplateResolver{
  /**
   * Order of preference for layout resolution should be:<br/>
   * <ol>
   *  <li>LayoutSelector choice</li>
   *  <li>localisation</li>
   *  <li>file-type (mustache, ssp, jade, scaml etc)</li>
   * </ol>
   *
   */
  def resolveLayout(request: Request, layout: Layout): Template

   /** Order of preference for view template resolution should be:<br/>
   * <ol>
   *  <li>TemplateSuffixSelector choice</li>
   *  <li>localisation</li>
   *  <li>file-type (mustache, ssp, jade, scaml etc)</li>
   * </ol>
   */
  def resolveViewTemplate(request: Request): Template

   /** Order of preference for template resolution should be (this is with an "absolute path", except for selector and locale):<br/>
   * <ol>
   *  <li>localisation</li>
   *  <li>file-type (mustache, ssp, jade, scaml etc)</li>
   * </ol>
   */
  def resolveTemplate(request: Request, path: String): Template

  def getAbsoluteResource(uri: String): String
}