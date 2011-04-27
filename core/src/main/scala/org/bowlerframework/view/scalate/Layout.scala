package org.bowlerframework.view.scalate

import java.io.PrintWriter
import org.bowlerframework.{Response, Request, RequestScope}

/**
 * Created by IntelliJ IDEA.
 * User: wfaler
 * Date: 21/04/2011
 * Time: 01:16
 * To change this template use File | Settings | File Templates.
 */

trait Layout{
  def parentLayout: Option[Layout] = None
  def render(request: Request, response: Response, viewModel: Map[String, Any], childView: String)
}


object Layout{

  def activeLayout(request: Request): Option[Layout] = {
    request.scopeDetails.get("activeLayout") match{
      case None => {
        TemplateRegistry.defaultLayout(request) match{
          case None => None
          case Some(layout) => Some(layout)
        }
      }
      case Some(layout) => Some(layout.asInstanceOf[Layout])
    }
  }

  def layout(myLayout: Layout): Unit = layout(myLayout, RequestScope.request)

  def layout(layout: Layout, request: Request): Unit ={
    if(request != null){
      request.scopeDetails.put("activeLayout", layout)
    }else{
      throw new IllegalStateException("cannot set activeLayout outside of a Request/Response cycle")
    }
  }
}