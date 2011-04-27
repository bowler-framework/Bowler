package org.bowlerframework.view.scalate

import org.bowlerframework.{Response, Request}
import java.io.{StringWriter, PrintWriter}

/**
 * Created by IntelliJ IDEA.
 * User: wfaler
 * Date: 25/04/2011
 * Time: 21:07
 * To change this template use File | Settings | File Templates.
 */

class DefaultLayout(val name: String, viewId: String = "doLayout", parent: Option[Layout] = None, layoutModel: Option[LayoutModel] = None) extends Layout{

  override def parentLayout = parent

  def render(request: Request, response: Response, viewModel: Map[String, Any], childView: String) = {
    val model = {
      layoutModel match{
        case None => Map(viewId -> childView)
        case Some(layoutModel) => layoutModel.model(request, viewModel, Tuple2(viewId, childView))
      }
    }
    val stringWriter = new StringWriter
    val writer: PrintWriter = {
      parentLayout match{
        case None => response.getWriter
        case Some(parent) => new PrintWriter(stringWriter)
      }
    }
    val engine = RenderEngine.getEngine
    val parent = TemplateRegistry.templateResolver.resolveLayout(request, name)
    val responseContext = new BowlerRenderContext(parent.uri, engine, writer)
    responseContext.render(parent.uri, model)
    if (parentLayout != None)
      parentLayout.get.render(request, response, viewModel, stringWriter.toString)
  }

}

object DefaultLayout{
  def apply(name: String, viewId: String = "doLayout", parentLayout: Option[Layout] = None, layoutModel: Option[LayoutModel] = None):DefaultLayout ={
    return new DefaultLayout(name, viewId, parentLayout, layoutModel)
  }

  def apply(name: String, parentLayout: Option[Layout]): DefaultLayout ={
    return new DefaultLayout(name, "doLayout", parentLayout, None)
  }
}