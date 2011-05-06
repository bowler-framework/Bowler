package org.bowlerframework.jvm

import com.recursivity.commons.bean.{JavaIntegerTransformer, LongTransformer, JavaBooleanTransformer}
import collection.mutable.MutableList
import org.bowlerframework._


class DummyRequest(var method: HttpMethod, path: String, params: Map[String, Any], body: String, headers: Map[String, String] = Map("accept" -> "text/html", "Content-Type" -> "multipart/form-data"), session: Session = new DummySession) extends Request {
  private val intTransformer = new JavaIntegerTransformer
  private val longTransformer = new LongTransformer
  private val booleanTransformer = new JavaBooleanTransformer
  private var mappedPath: MappedPath = null

  private var locales = List("en_US")

  def setMappedPath(mappedPath: MappedPath) = {
    this.mappedPath = mappedPath
  }

  def getMappedPath = mappedPath

  def getRequestBodyAsString = body

  def getBooleanParameter(name: String) = params.get(name) match {
    case None => None
    case Some(s) => Option(booleanTransformer.toValue(s.toString).getOrElse(null).asInstanceOf[Boolean])
  }

  def getLongParameter(name: String) = params.get(name) match {
    case None => None
    case Some(s) => Option(longTransformer.toValue(s.toString).getOrElse(null).asInstanceOf[Long])
  }

  def getIntParameter(name: String) = params.get(name) match {
    case None => None
    case Some(s) => Option(intTransformer.toValue(s.toString).getOrElse(null).asInstanceOf[Int])
  }

  def getParameter(name: String) = Option(params.get(name).getOrElse(null))

  def getParameterValues(name: String) = Option(params.get(name).getOrElse(null).asInstanceOf[List[Any]])

  def getParameterNames = params.keys

  def getStringParameter(name: String) = Option(params.get(name).getOrElse(null).asInstanceOf[String])


  def getParameterMap = params

  def getSession = session

  def isSecure = false

  def getServerName = "localhost"

  def getPath = path

  def getHeaders(name: String) = {
    try {
      List(headers(name))
    } catch {
      case e: NoSuchElementException => List[String]()
    }
  }

  def getHeader(name: String) = Option(headers.get(name).getOrElse(null))

  def getHeaderNames: List[String] = {
    val list = new MutableList[String]
    headers.keys.foreach(f => list += f)
    list.toList
  }

  def getLocales = locales

  def setLocales(locales: List[String]) = {
    this.locales = locales
  }

  def getAccept = headers.get("accept")

  def getMethod = method

  def setMethod(method: HttpMethod) = (this.method = method)

  def getContentType: Option[String] = {
    try {
      return Some(headers("Content-Type"))
    } catch {
      case e: NoSuchElementException => return None
    }

  }

}