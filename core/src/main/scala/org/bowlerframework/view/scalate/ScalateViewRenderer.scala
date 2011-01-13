package org.bowlerframework.view.scalate

import org.bowlerframework.{Response, Request}
import collection.mutable.HashMap
import org.bowlerframework.model.AliasRegistry
import org.bowlerframework.view.{ViewModel, ViewRenderer}
import org.fusesource.scalate.DefaultRenderContext
import com.recursivity.commons.StringInputStreamReader
import java.io.{StringWriter, PrintWriter}

/**
 * A ViewRenderer that uses Scalate templates to render views
 */

class ScalateViewRenderer extends ViewRenderer with StringInputStreamReader{
  def onError(request: Request, response: Response, exception: Exception) = null

  def renderView(request: Request, response: Response, models: Any*) = {
    val map = new HashMap[String, Any]
    models.foreach(f =>{
      val alias = getAlias(f)
      val value = getValue(f)
      map.put(alias, value)
    })

    val model = map.toMap
    render(request, response, model)
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

    val writer = {
      if(layout.parentLayout == None)
        return response.getWriter
      else{
        new PrintWriter(stringWriter)
    }}
    val responseContext = new DefaultRenderContext(TemplateRegistry.templateResolver.resolveLayout(request, layout).uri, engine, writer)
    responseContext.render(parent.uri, layoutModel.toMap)
    if(layout.parentLayout != None)
      renderLayout(layout.parentLayout.get, request, response, viewModel, stringWriter.toString)

  }

  private def getAlias(any: Any): String = {
    if(any.isInstanceOf[ViewModel])
      return any.asInstanceOf[ViewModel].alias
    else if(any.isInstanceOf[Tuple2[String, _]])
      return any.asInstanceOf[Tuple2[String, _]]._1
    else
      return AliasRegistry.getModelAlias(any).get
  }

  private def getValue(any: Any): Any = {
    if(any.isInstanceOf[ViewModel])
      return any.asInstanceOf[ViewModel].value
    else if(any.isInstanceOf[Tuple2[String, _]])
      return any.asInstanceOf[Tuple2[String, Any]]._2
    else
      return any
  }
}