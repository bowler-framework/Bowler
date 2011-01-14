package org.bowlerframework.view.scalate

import org.bowlerframework.view.{ViewRenderer}
import org.fusesource.scalate.DefaultRenderContext
import com.recursivity.commons.StringInputStreamReader
import java.io.{StringWriter, PrintWriter}
import org.bowlerframework.exception.{ValidationException, HttpException}
import collection.mutable.{MutableList, HashMap}
import org.bowlerframework.{HTTP, Response, Request}
import org.bowlerframework.http.BowlerHttpSession

/**
 * A ViewRenderer that uses Scalate templates to render views
 */

class ScalateViewRenderer extends ViewRenderer with StringInputStreamReader{

  def onError(request: Request, response: Response, exception: Exception) = {
    response.setContentType("application/xhtml+xml")
    if(classOf[HttpException].isAssignableFrom(exception.getClass)){
      if(exception.isInstanceOf[ValidationException]){
        val validations = exception.asInstanceOf[ValidationException]
        request.getSession.setErrors(validations.errors)
        if(request.getSession.getLastGetPath != None)
          response.sendRedirect(request.getSession.getLastGetPath.get)
        println("ERROR HTTP " + request.getMethod + " : " + request.getSession)
      }else{
        val http = exception.asInstanceOf[HttpException]
        response.sendError(http.code)
        // render error pages?
      }

    }else{
      throw exception
    }
  }

  def renderView(request: Request, response: Response, models: Any*) = {
    val model = new HashMap[String, Any]
    models.foreach(f =>{
      val alias = getModelAlias(f)
      val value = getModelValue(f)
      model.put(alias, value)
    })
    if(request.getSession.getErrors != None){
      val list = new MutableList[String]
      request.getSession.getErrors.get.foreach(f => list += f._2)
      model += "validationErrors" -> list.toList
      request.getSession.removeErrors
    }

    render(request, response, model.toMap)
  }

  def renderView(request: Request, response: Response) = {
    val model = new HashMap[String, Any]
    if(request.getSession.getErrors != None){
      val list = new MutableList[String]
      request.getSession.getErrors.get.foreach(f => list += f._2)
      model += "validationErrors" -> list.toList
      request.getSession.removeErrors
    }
    render(request, response, model.toMap)
  }


  private def render(request: Request, response: Response, model: Map[String, Any]) ={
    response.setContentType("application/xhtml+xml")

    val view = TemplateRegistry.templateResolver.resolveViewTemplate(request)
    val engine = RenderEngine.getEngine
    val writer = new StringWriter
    val pw = new PrintWriter(writer)
    val context = new DefaultRenderContext(view.uri, engine, pw)
    context.render(view.uri, model)
    val viewValue = writer.toString
    val layout = TemplateRegistry.getLayout(request)
    renderLayout(layout, request, response, model, viewValue)

    if(request.getMethod == HTTP.GET){
      request.getSession.setLastGetPath(HTTP.relativeUrl(request.getPath))
    }
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