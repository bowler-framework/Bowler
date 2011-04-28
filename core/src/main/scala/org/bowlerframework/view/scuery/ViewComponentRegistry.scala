package org.bowlerframework.view.scuery

import org.bowlerframework.view.ViewPath
import collection.mutable.HashMap
import org.bowlerframework.{Request, RequestScope}

/**
 * Created by IntelliJ IDEA.
 * User: wfaler
 * Date: 23/04/2011
 * Time: 01:37
 * To change this template use File | Settings | File Templates.
 */

object ViewComponentRegistry{
  private val registry = new HashMap[ViewPath, Function1[Map[String, Any], MarkupContainer]]

  def register(viewPath: ViewPath, component: Function1[Map[String, Any], MarkupContainer]): Unit = {
    registry.put(viewPath, component)
  }

  def register(request: Request, component: MarkupContainer): Unit = {
    request.scopeDetails.put("squeryView", component)
  }

  def register(component: MarkupContainer): Unit = register(RequestScope.request, component)

  def apply(request: Request, resources: Map[String, Any]): Option[MarkupContainer] = {
    request.scopeDetails.get("squeryView") match{
      case None => {
        registry.get(ViewPath(request.getMethod, request.getMappedPath)) match{
          case None => None
          case Some(s) => Option(s(resources))
        }
      }
      case Some(component) => Some(component.asInstanceOf[MarkupContainer])
    }
  }

}