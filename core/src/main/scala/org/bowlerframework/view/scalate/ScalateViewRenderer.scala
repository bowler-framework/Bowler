package org.bowlerframework.view.scalate

import org.bowlerframework.{Response, Request}
import collection.mutable.HashMap
import org.bowlerframework.view.{ViewRenderer}
import org.fusesource.scalate.DefaultRenderContext
import com.recursivity.commons.StringInputStreamReader
import java.io.{StringWriter, PrintWriter}

/**
 * A ViewRenderer that uses Scalate templates to render views
 */

class ScalateViewRenderer extends ViewRenderer with StringInputStreamReader{
  def onError(request: Request, response: Response, exception: Exception) = null

  def renderView(request: Request, response: Response, models: Any*) = {
    val model = new HashMap[String, Any]
    models.foreach(f =>{
      val alias = getModelAlias(f)
      val value = getModelValue(f)
      model.put(alias, value)
    })
    render(request, response, model.toMap)
  }

  def renderView(request: Request, response: Response) = {
    render(request, response, Map[String, Any]())
  }


  private def render(request: Request, response: Response, model: Map[String, Any]) ={
    val view = TemplateRegistry.templateResolver.resolveViewTemplate(request)
    val engine = RenderEngine.getEngine
    val writer = new StringWriter
    val pw = new PrintWriter(writer)
    val context = new DefaultRenderContext(view.uri, engine, pw)
    context.render(view.uri, model)
    val viewValue = writer.toString
    val layout = TemplateRegistry.getLayout(request)
    renderLayout(layout, request, response, model, viewValue)
  }

  private def renderLayout(layout: Layout, request: Request, response: Response, viewModel: Map[String, Any], view: String){
    val engine = RenderEngine.getEngine
    var layoutModel = layout.layoutModel.model(request, viewModel, view)

    val parent = TemplateRegistry.templateResolver.resolveLayout(request, layout)
    val stringWriter = new StringWriter
    var writer: PrintWriter = null
    if(layout.parentLayout == None)
      writer = response.getWriter
    else{
      writer = new PrintWriter(stringWriter)
    }


    val responseContext = new DefaultRenderContext(TemplateRegistry.templateResolver.resolveLayout(request, layout).uri, engine, writer)
    responseContext.render(parent.uri, layoutModel.toMap)
    if(layout.parentLayout != None)
      renderLayout(layout.parentLayout.get, request, response, viewModel, stringWriter.toString)
  }

}