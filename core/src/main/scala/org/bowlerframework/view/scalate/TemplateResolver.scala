package org.bowlerframework.view.scalate

/**
 * Created by IntelliJ IDEA.
 * User: wfaler
 * Date: 05/01/2011
 * Time: 22:16
 * To change this layout use File | Settings | File Templates.
 */

trait TemplateResolver{
  // order of preference is: locale, type
  def resolveLayout(layout: Layout)
}