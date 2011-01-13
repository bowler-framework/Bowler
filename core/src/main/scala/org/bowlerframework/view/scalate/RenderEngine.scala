package org.bowlerframework.view.scalate

import org.fusesource.scalate.TemplateEngine
import org.fusesource.scalate.util.{Resource, FileResourceLoader}

/**
 * Singleton Object that holds reference to a Scalate TemplateEngine
 */

object RenderEngine{
  val engine = new TemplateEngine
  engine.allowCaching = true
  engine.allowReload = false

  engine.resourceLoader = new FileResourceLoader {
   override def resource(uri: String): Option[Resource] = Some(Resource.fromText(uri, TemplateRegistry.templateResolver.getAbsoluteResource(uri)))
  }

  def getEngine = engine
}