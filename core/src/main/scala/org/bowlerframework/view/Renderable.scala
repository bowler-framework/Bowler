package org.bowlerframework.view

import org.bowlerframework.{BowlerConfigurator, Response, RequestScope, Request}
import scuery.{MarkupContainer, ViewComponentRegistry}

/**
 * Created by IntelliJ IDEA.
 * User: wfaler
 * Date: 28/12/2010
 * Time: 20:49
 * To change this activeLayout use File | Settings | File Templates.
 */
trait Renderable {

  def renderWith(component: MarkupContainer): Unit = renderWith(component, RequestScope.request, RequestScope.response)

  def renderWith(component: MarkupContainer, request: Request, response: Response): Unit = {
    ViewComponentRegistry.register(request, component)
    render(request, response)
  }

  def renderWith(component: MarkupContainer, models: Any*): Unit = {
    ViewComponentRegistry.register(RequestScope.request, component)
    renderSeq(RequestScope.request, RequestScope.response,  models.toSeq)
  }

  def renderWith(component: MarkupContainer, request: Request, response: Response, models: Any*): Unit = {
    ViewComponentRegistry.register(request, component)
    renderSeq(request, response, models.toSeq)
  }

  def renderWith(viewPath: ViewPath): Unit = renderWith(viewPath, RequestScope.request, RequestScope.response)

  def renderWith(viewPath: ViewPath, request: Request, response: Response): Unit = {
    request.setMappedPath(viewPath.path)
    request.setMethod(viewPath.method)
    render(request, response)
  }

  def renderWith(viewPath: ViewPath, models: Any*): Unit = {
    RequestScope.request.setMappedPath(viewPath.path)
    RequestScope.request.setMethod(viewPath.method)
    renderSeq(RequestScope.request, RequestScope.response, models.toSeq)
  }

  def renderWith(viewPath: ViewPath, request: Request, response: Response, models: Any*): Unit = {
    request.setMappedPath(viewPath.path)
    request.setMethod(viewPath.method)
    renderSeq(request, response, models.toSeq)
  }

  def render: Unit = render(RequestScope.request, RequestScope.response)

  def render(request: Request, response: Response): Unit = {
    val renderer = BowlerConfigurator.resolveViewRenderer(request)
    renderer.renderView(request, response, List[Any]())
  }

  def render(models: Any*): Unit = {
    renderSeq(RequestScope.request, RequestScope.response, models.toSeq)
  }

  def render(request: Request, response: Response, models: Any*): Unit = renderSeq(request, response, models.toSeq)

  private def renderSeq(request: Request, response: Response, models: Seq[Any]): Unit = {
    val renderer = BowlerConfigurator.resolveViewRenderer(request)
    renderer.renderView(request, response, models)
  }

}