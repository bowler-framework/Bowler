package org.bowlerframework.view.scalate

import org.fusesource.scalate.TemplateEngine
import org.fusesource.scalate.util.{Resource, FileResourceLoader}
import java.text.{DecimalFormat, NumberFormat}

/**
 * Singleton Object that holds reference to a Scalate TemplateEngine
 */

object RenderEngine{
  private var engine: TemplateEngine = null
  reset

  private var df = new DecimalFormat
  df.setGroupingUsed(false)
  private var nf: NumberFormat = df

  def getEngine = engine

  def setNumberFormat(nf: NumberFormat) = {this.nf = nf}

  def numberFormat = nf

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