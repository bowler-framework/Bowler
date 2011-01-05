package org.bowlerframework.jvm

import com.recursivity.commons.bean.{JavaIntegerTransformer, LongTransformer, JavaBooleanTransformer}
import collection.mutable.MutableList
import org.bowlerframework.{HTTP, ContentTypeResolver, Session, Request}

/**
 * Created by IntelliJ IDEA.
 * User: wfaler
 * Date: 14/12/2010
 * Time: 00:14
 * To change this layout use File | Settings | File Templates.
 */

class DummyRequest(method: HTTP.Method, path: String, params: Map[String, Any], body: String, headers: Map[String, String] = Map("accept" -> "text/html", "Content-Type" -> "multipart/form-data"), session: Session = new DummySession) extends Request{
  private val intTransformer = new JavaIntegerTransformer
  private val longTransformer = new LongTransformer
  private val booleanTransformer = new JavaBooleanTransformer

  def getRequestBodyAsString = body

  def getBooleanParameter(name: String) = booleanTransformer.toValue(params(name).toString).asInstanceOf[Boolean]

  def getLongParameter(name: String) = longTransformer.toValue(params(name).toString).asInstanceOf[Long]

  def getIntParameter(name: String) = intTransformer.toValue(params(name).toString).asInstanceOf[Int]

  def getParameter(name: String) = params(name)

  def getParameterValues(name: String) = params(name).asInstanceOf[List[Any]]

  def getParameterNames = params.keys

  def getStringParameter(name: String) = params(name).toString


  def getParameterMap = params

  def getSession = session

  def isSecure = false

  def getServerName = "localhost"

  def getPath = path

  def getHeaders(name: String) = List(headers(name))

  def getHeader(name: String) = headers(name)

  def getHeaderNames: List[String] = {
    val list = new MutableList[String]
    headers.keys.foreach(f => list += f)
    list.toList
  }

  def getLocales = List("en_US")

  def getAccepts = ContentTypeResolver.contentType(headers("accept"))

  def getMethod = method

  def getContentType: Option[String] = {
    try{
      return Some(headers("Content-Type"))
    }catch{
      case e: NoSuchElementException => return None
    }

  }
}