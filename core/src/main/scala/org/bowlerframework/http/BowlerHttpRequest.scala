package org.bowlerframework.http

import javax.servlet.http.HttpServletRequest
import collection.mutable.MutableList
import com.recursivity.commons.bean._
import com.recursivity.commons.StringInputStreamReader
import org.bowlerframework._


class BowlerHttpRequest(path: String, val request: HttpServletRequest, params: Map[String, Any]) extends Request with StringInputStreamReader {
  val session = new BowlerHttpSession(request.getSession(true))

  private val intTransformer = new JavaIntegerTransformer
  private val longTransformer = new LongTransformer
  private val booleanTransformer = new JavaBooleanTransformer
  private var method: HttpMethod = null

  private var body: String = null

  private var mappedPath: MappedPath = null

  def setMappedPath(mappedPath: MappedPath) = {
    this.mappedPath = mappedPath
  }

  def getMappedPath = mappedPath

  def isSecure = request.isSecure

  def getServerName = request.getServerName

  def getBooleanParameter(name: String) = params.get(name) match{
      case None => None
      case Some(s) => Option(booleanTransformer.toValue(s.toString).getOrElse(null).asInstanceOf[Boolean])
    }

  def getLongParameter(name: String) = params.get(name) match{
      case None => None
      case Some(s) => Option(longTransformer.toValue(s.toString).getOrElse(null).asInstanceOf[Long])
    }

  def getIntParameter(name: String) = params.get(name) match{
      case None => None
      case Some(s) => Option(intTransformer.toValue(s.toString).getOrElse(null).asInstanceOf[Int])
    }

  def getParameter(name: String) = params.get(name).asInstanceOf[Option[String]]

  protected def paramValuesToList(values: Any) = values match {
    case (s: String) => List(s)
    case (la: List[Any]) => la
  }

  override def getParameterValues(name: String) = params.get(name).map(paramValuesToList _).getOrElse(List[Any]())

  def getParameterNames = params.keys


  def getParameterMap = params

  def getStringParameter(name: String) = params.get(name).asInstanceOf[Option[String]]

  def getHeaderNames: List[String] = {
    val iter = request.getHeaderNames
    val list = new MutableList[String]
    while (iter.hasMoreElements)
      list += iter.nextElement.toString
    return list.toList
  }

  def getHeaders(name: String): List[String] = {
    val enum = request.getHeaders(name)
    val list = new MutableList[String]
    while (enum.hasMoreElements)
      list += enum.nextElement.toString
    return list.toList
  }

  def getHeader(name: String) = Option(request.getHeader(name))

  def getSession = session

  def getPath = path

  def getHttpServletRequest = request

  def getRequestBodyAsString: String = {
    if (body == null) {
      val is = this.getInputStream
      try {
        body = load(is)
      } finally {
        is.close
      }
    }
    return body
  }

  def getInputStream = request.getInputStream

  def getLocales: List[String] = {
    val enum = request.getLocales
    val list = new MutableList[String]
    while (enum.hasMoreElements)
      list += enum.nextElement.toString
    return list.toList
  }

  def getAccept = Option(request.getHeader("accept"))

  def getMethod: HttpMethod = {
    if (method == null) {
      request.getMethod match {
        case "GET" => return GET
        case "PUT" => return PUT
        case "POST" => return POST
        case "DELETE" => return DELETE
      }
    } else
      return method
  }


  def setMethod(method: HttpMethod) = {
    this.method = method
  }

  def getContentType: Option[String] = {
    val string = request.getContentType
    return Option(string)
  }

}
