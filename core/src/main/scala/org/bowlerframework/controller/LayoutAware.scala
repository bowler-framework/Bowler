package org.bowlerframework.controller

import org.bowlerframework.view.scalate.{TemplateRegistry, Layout}
import org.bowlerframework.{Request, RequestScope}

/**
 * Created by IntelliJ IDEA.
 * User: wfaler
 * Date: 23/04/2011
 * Time: 00:30
 * To change this template use File | Settings | File Templates.
 */

trait LayoutAware{ this: Controller => {
    this.addBefore{(request, response) => setup(request)}
   }

  def layout(layout: Layout){
    if(RequestScope.request == null){
      TemplateRegistry.controllerLayouts.put(this.getClass, layout)
    }else{
      RequestScope.request.scopeDetails.put("activeLayout", layout)
    }
  }


  private def setup(request: Request): Unit = {
    TemplateRegistry.controllerLayouts.get(this.getClass) match{
      case None => {
        TemplateRegistry.defaultLayout(request) match{
          case None => {}
          case Some(layout) => request.scopeDetails.put("activeLayout", layout)
        }
      }
      case Some(template) => request.scopeDetails.put("activeLayout", template)
    }
  }
}