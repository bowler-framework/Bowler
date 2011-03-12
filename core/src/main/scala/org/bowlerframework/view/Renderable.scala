package org.bowlerframework.view

import org.bowlerframework.{BowlerConfigurator, Response, RequestScope, Request}

/**
 * Created by IntelliJ IDEA.
 * User: wfaler
 * Date: 28/12/2010
 * Time: 20:49
 * To change this layout use File | Settings | File Templates.
 */
trait Renderable{

  def render: Unit = render(RequestScope.request,RequestScope.response)

  def render(models: Any*):Unit ={
    renderSeq(RequestScope.request,RequestScope.response, models.toSeq)
  }

  def render(request: Request, response: Response, models: Any*): Unit = renderSeq(request,response, models.toSeq)

  private def renderSeq(request: Request, response: Response, models: Seq[Any]): Unit = {
    val renderer = BowlerConfigurator.resolveViewRenderer(request)
    renderer.renderView(request, response, models)
  }

  def render(request: Request, response: Response): Unit = {
    val renderer = BowlerConfigurator.resolveViewRenderer(request)
    renderer.renderView(request, response, List[Any]())
  }
}