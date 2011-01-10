package org.bowlerframework.view.scalate

import org.fusesource.scalate.TemplateEngine

/**
 * Singleton Object that holds reference to a Scalate TemplateEngine
 */

object RenderEngine{
  val engine = new TemplateEngine
  engine.allowCaching = true
  engine.allowReload = false

  def getEngine = engine
}