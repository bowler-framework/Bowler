package org.bowlerframework.view.scalate

import org.bowlerframework.{Response, Request}
import org.bowlerframework.view.squery.Component
import java.io.{PrintWriter, StringWriter}

/**
 * Created by IntelliJ IDEA.
 * User: wfaler
 * Date: 25/04/2011
 * Time: 23:22
 * To change this template use File | Settings | File Templates.
 */

class SqueryLayout(componentCreator: (Request) => Component, viewSelector: String, parent: Option[Layout] = None) extends Layout{

  override def parentLayout = parent

  def render(request: Request, response: Response, childView: String) = {
    val component = componentCreator(request)
    component.$(viewSelector).contents = childView

    val stringWriter = new StringWriter
    val writer: PrintWriter = {
      parentLayout match{
        case None => response.getWriter
        case Some(parent) => new PrintWriter(stringWriter)
      }
    }

    writer.write(component.render.toString)
    if(parentLayout.get != None)
      parentLayout.get.render(request, response, stringWriter.toString)
  }
}