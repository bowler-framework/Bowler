package org.bowlerframework.examples

import org.bowlerframework.Request
import collection.mutable.HashMap
import util.matching.Regex
import java.io.{StringWriter, PrintWriter}
import org.fusesource.scalate.DefaultRenderContext
import org.bowlerframework.view.scalate.{RenderEngine, LayoutModel}

/**
 * Model for the default template
 */

class ParentLayoutModel extends LayoutModel{
  def model(request: Request, viewModel: Map[String, Any], childView: String): Map[String, Any] = {
    val map = new HashMap[String, Any]
    // lets start by adding the childView so it's guaranteed to appear!
    map += "doLayout" -> childView

    // lets see if we want to add a tab panel to this layout!
    val regex = new Regex("^.*/composable/.*$")
    if(regex.pattern.matcher(request.getPath).matches){
      // using a raw Scalate Template Engine with absolute URI to get and render the template.
      val engine = RenderEngine.getEngine
      val stringWriter = new StringWriter
      val writer = new PrintWriter(stringWriter)
      val responseContext = new DefaultRenderContext("/layouts/tabs.mustache", engine, writer)
      responseContext.render("/layouts/tabs.mustache", Map[String, Any]())
      map += "tabsPanel" -> stringWriter.toString
    }

    return map.toMap
  }
}