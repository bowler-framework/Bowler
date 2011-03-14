package org.bowlerframework.view.squery

import org.bowlerframework.view.scalate.ClasspathTemplateResolver
import org.bowlerframework.RequestScope
import xml.{XML, NodeSeq}
import java.io.{IOException, StringReader}

/**
 * Created by IntelliJ IDEA.
 * User: wfaler
 * Date: 12/03/2011
 * Time: 18:48
 * To change this template use File | Settings | File Templates.
 */

trait Component{
  val templateResolver = new ClasspathTemplateResolver

  def render: NodeSeq = getTemplate(this.getClass)

  private def uri(cls: Class[_]): String = "/" + cls.getName.replace(".", "/")

  private def getTemplate(cls: Class[_]): NodeSeq = {
    try{
      XML.load(new StringReader(templateResolver.resolveResource(uri(cls), Component.types, Component.locales).template)).asInstanceOf[NodeSeq]
    }catch{
      case e: IOException => {
        if(cls.getSuperclass != null && classOf[Component].isAssignableFrom(cls.getSuperclass))
          getTemplate(cls.getSuperclass)
        else
          throw new IOException("Can't find any markup for Component of type " + cls.getName)
      }
    }
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