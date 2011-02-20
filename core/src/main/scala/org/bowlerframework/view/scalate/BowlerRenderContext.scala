package org.bowlerframework.view.scalate

import java.io.PrintWriter
import org.fusesource.scalate.{TemplateEngine, DefaultRenderContext}
import util.DynamicVariable
import java.text.{DecimalFormat, NumberFormat}

/**
 * Created by IntelliJ IDEA.
 * User: wfaler
 * Date: 19/01/2011
 * Time: 22:52
 * To change this template use File | Settings | File Templates.
 */

class BowlerRenderContext(uri: String, engine: TemplateEngine, pw: PrintWriter) extends DefaultRenderContext(uri, engine, pw){

  this.numberFormat = RenderEngine.numberFormat

  override def render(path: String, attributeMap: Map[String, Any]) = {
    BowlerRenderContext._modelContext.withValue(attributeMap){
      super.render(path, attributeMap)
    }
  }
}

object BowlerRenderContext{
  private val _modelContext = new DynamicVariable[Map[String, Any]](null)
  def contextModel = _modelContext value
}

