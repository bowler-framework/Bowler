package org.bowlerframework.view.scalate

import org.fusesource.scalate.TemplateEngine

/**
 * Created by IntelliJ IDEA.
 * User: wfaler
 * Date: 03/01/2011
 * Time: 20:51
 * To change this layout use File | Settings | File Templates.
 */

object RenderEngine{
  val engine = new TemplateEngine
  engine.allowCaching = true
  engine.allowReload = false

  def getEngine = engine
}