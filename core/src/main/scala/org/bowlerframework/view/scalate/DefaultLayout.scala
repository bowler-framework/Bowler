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

class DefaultLayout(val name: String, parent: Option[Layout] = None, model: LayoutModel = new NoopLayoutModel) extends Layout{

  override def layoutModel = model
  override def parentLayout = parent

  def render(request: Request, response: Response, viewModel: Map[String, Any]) = {
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
    responseContext.render(parent.uri, viewModel.toMap)
    if (parentLayout != None)
      parentLayout.get.render(request, response, parentLayout.get.layoutModel.model(request, viewModel, stringWriter.toString))
  }

}

object DefaultLayout{
  def apply(name: String, parentLayout: Option[Layout] = None, layoutModel: LayoutModel = new NoopLayoutModel):DefaultLayout ={
    return new DefaultLayout(name, parentLayout, layoutModel)
  }
}