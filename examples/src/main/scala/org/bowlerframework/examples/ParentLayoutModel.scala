package org.bowlerframework.examples

import org.bowlerframework.Request
import collection.mutable.HashMap
import util.matching.Regex
import java.io.{StringWriter, PrintWriter}
import org.fusesource.scalate.DefaultRenderContext
import org.bowlerframework.view.scalate.{ComponentRenderSupport, RenderEngine, LayoutModel}

/**
 * Model for the default template
 */

class ParentLayoutModel extends LayoutModel{
  def model(request: Request, viewModel: Map[String, Any], viewIdAndValue: Tuple2[String, String]): Map[String, Any] = {
    val map = new HashMap[String, Any]
    // lets start by adding the childView so it's guaranteed to appear!
    map += viewIdAndValue._1 -> viewIdAndValue._2

    // lets see if we want to add a tab panel to this layout!
    val regex = new Regex("^.*/composable/.*$")
    if(regex.pattern.matcher(request.getPath).matches){
      map += "tabsPanel" -> TabsComponent.show       // uses Component
    }

    return map.toMap
  }
}

object TabsComponent extends ComponentRenderSupport{

  def show = {
    render("hello")
  }

}