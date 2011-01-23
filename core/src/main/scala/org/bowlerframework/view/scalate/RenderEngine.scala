package org.bowlerframework.view.scalate

import org.fusesource.scalate.TemplateEngine
import org.fusesource.scalate.util.{Resource, FileResourceLoader}

/**
 * Singleton Object that holds reference to a Scalate TemplateEngine
 */

object RenderEngine{
  private var engine: TemplateEngine = null
  reset

  def getEngine = engine

  def reset = {
    engine = new TemplateEngine
    engine.allowCaching = true
    engine.allowReload = false
    engine.resourceLoader = new FileResourceLoader {
      override def resource(uri: String): Option[Resource] = {
        Some(Resource.fromText(uri, TemplateRegistry.templateResolver.getAbsoluteResource(uri)))
      }
    }
  }
}