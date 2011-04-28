package org.bowlerframework.view.scuery

import org.bowlerframework.view.scalate.ClasspathTemplateResolver
import xml.{XML, NodeSeq}
import java.io.{IOException, StringReader}
import java.util.concurrent.ConcurrentHashMap
import org.bowlerframework.{RequestResolver, RequestScope}

/**
 * Created by IntelliJ IDEA.
 * User: wfaler
 * Date: 12/03/2011
 * Time: 18:48
 * To change this template use File | Settings | File Templates.
 */

trait MarkupContainer {
  def render: NodeSeq = MarkupContainer.getTemplate(this.getClass, MarkupContainer.localisationPreferences)
}

object MarkupContainer {
  private val templateCache = new ConcurrentHashMap[Class[_], ConcurrentHashMap[String, Option[NodeSeq]]]
  private val templateResolver = new ClasspathTemplateResolver
  val types = List(".html", ".xhtml", ".xml")

  var requestResolver: RequestResolver = new RequestResolver {
    def request = RequestScope.request
  }

  private def uri(cls: Class[_]): String = "/" + cls.getName.replace(".", "/")

  private def localisationPreferences: List[String] = {
    if (requestResolver.request != null && requestResolver.request.getLocales != null)
      return requestResolver.request.getLocales
    else return Nil
  }


  private def getTemplate(cls: Class[_], locales: List[String]): NodeSeq = {
    if (templateCache.get(cls) == null)
      templateCache.put(cls, new ConcurrentHashMap[String, Option[NodeSeq]])
    val map = templateCache.get(cls)
    try {
      if (locales == Nil) {
        val option = map.get("default")
        if (option == null) throw new NoSuchElementException
        if (option == None)
          return getTemplate(cls.getSuperclass, localisationPreferences)
        else return option.get
      } else {
        val option = map.get(locales.head)
        if (option == null) throw new NoSuchElementException
        if (option == None) {
          val newLocales = locales.drop(1)
          return getTemplate(cls, newLocales)
        } else return option.get
      }
    } catch {
      case e: NoSuchElementException => {
        try {
          val nodeSeq = XML.load(new StringReader(templateResolver.resolveResource(uri(cls), MarkupContainer.types, {
            if (locales != Nil) List(locales.head); else Nil
          }).template)).asInstanceOf[NodeSeq]
          map.put({
            if (locales != Nil) locales.head; else "default"
          }, Some(nodeSeq))
          return nodeSeq
        } catch {
          case e: IOException => {
            map.put({
              if (locales != Nil) locales.head; else "default"
            }, None)
            if (locales != Nil) {
              val newLocales = locales.drop(1)
              getTemplate(cls, newLocales)
            } else if (cls.getSuperclass != null && classOf[MarkupContainer].isAssignableFrom(cls.getSuperclass) && locales == Nil) {
              getTemplate(cls.getSuperclass, localisationPreferences)
            }
            else
              throw new IOException("Can't find any markup for Component of type " + cls.getName)
          }
        }
      }
    }

  }
}