package org.bowlerframework.view.squery

import org.bowlerframework.view.scalate.ClasspathTemplateResolver
import org.bowlerframework.RequestScope
import java.io.StringReader
import xml.{XML, NodeSeq}

/**
 * Created by IntelliJ IDEA.
 * User: wfaler
 * Date: 12/03/2011
 * Time: 18:48
 * To change this template use File | Settings | File Templates.
 */

trait Component{
  val templateResolver = new ClasspathTemplateResolver
  val uri = "/" + this.getClass.getName.replace(".", "/")

  def render: NodeSeq = {
    XML.load(new StringReader(templateResolver.resolveResource(uri, Component.types, Component.locales).template)).asInstanceOf[NodeSeq]
  }
}

object Component{
  val types = List(".html", ".xhtml", ".xml")

  def locales: List[String] = {
    if(RequestScope.request != null && RequestScope.request.getLocales != null)
      return RequestScope.request.getLocales
    else return List[String]()
  }
}