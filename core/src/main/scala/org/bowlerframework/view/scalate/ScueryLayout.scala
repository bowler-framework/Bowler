package org.bowlerframework.view.scalate

import org.bowlerframework.{Response, Request}
import org.bowlerframework.view.scuery.Component
import java.io.{StringReader, PrintWriter, StringWriter}
import org.xml.sax.SAXParseException

/**
 * Created by IntelliJ IDEA.
 * User: wfaler
 * Date: 25/04/2011
 * Time: 23:22
 * To change this template use File | Settings | File Templates.
 */

class ScueryLayout(componentCreator: (Map[String, Any]) => Component, viewSelector: String, parent: Option[Layout] = None) extends Layout{

  override def parentLayout = parent

  def render(request: Request, response: Response, viewModel: Map[String, Any], childView: String) = {
    val component = componentCreator(viewModel)
    try{
      component.$(viewSelector).contents = scala.xml.XML.load(new StringReader(childView))
    }catch{
      case e: SAXParseException => component.$(viewSelector).contents = childView
    }

    val stringWriter = new StringWriter
    val writer: PrintWriter = {
      parentLayout match{
        case None => response.getWriter
        case Some(parent) => new PrintWriter(stringWriter)
      }
    }

    writer.write(component.render.toString)
    if(parentLayout != None)
      parentLayout.get.render(request, response, viewModel, stringWriter.toString)
  }
}