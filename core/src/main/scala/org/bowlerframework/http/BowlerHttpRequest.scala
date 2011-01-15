package org.bowlerframework.http

import javax.servlet.http.HttpServletRequest
import collection.mutable.MutableList
import com.recursivity.commons.bean._
import com.recursivity.commons.StringInputStreamReader
import org.apache.commons.fileupload.FileItem
import org.bowlerframework.{MappedPath, HTTP, ContentTypeResolver, Request}

/**
 * Created by IntelliJ IDEA.
 * User: wfaler
 * Date: 13/12/2010
 * Time: 21:57
 * To change this layout use File | Settings | File Templates.
 */

class BowlerHttpRequest(path: String, val request: HttpServletRequest, params: Map[String, Any]) extends Request with StringInputStreamReader{
  val session = new BowlerHttpSession(request.getSession(true))

  private val intTransformer = new JavaIntegerTransformer
  private val longTransformer = new LongTransformer
  private val booleanTransformer = new JavaBooleanTransformer

  private var body: String = null

  private var mappedPath: MappedPath = null

  def setMappedPath(mappedPath: MappedPath) = {this.mappedPath = mappedPath}

  def getMappedPath = mappedPath

  def isSecure = request.isSecure

  def getServerName = request.getServerName

  def getBooleanParameter(name: String) = booleanTransformer.toValue(params(name).toString).get.asInstanceOf[Boolean]

  def getLongParameter(name: String) = longTransformer.toValue(params(name).toString).get.asInstanceOf[Long]

  def getIntParameter(name: String) = intTransformer.toValue(params(name).toString).get.asInstanceOf[Int]

  def getParameter(name: String) = params(name)

  def getParameterValues(name: String) = params(name).asInstanceOf[List[Any]]

  def getParameterNames = params.keys


  def getParameterMap = params

  def getStringParameter(name: String) = params(name).toString

  def getHeaderNames: List[String] = {
    val iter = request.getHeaderNames
    val list = new MutableList[String]
    while(iter.hasMoreElements)
      list += iter.nextElement.toString
    return list.toList
  }

  def getHeaders(name: String): List[String] = {
    val enum = request.getHeaders(name)
    val list = new MutableList[String]
    while(enum.hasMoreElements)
      list += enum.nextElement.toString
    return list.toList
  }

  def getHeader(name: String) = request.getHeader(name)

  def getSession = session

  def getPath = path

  def getHttpServletRequest = request

  def getRequestBodyAsString: String = {
    if(body == null){
      val is = this.getInputStream
      try{
        body = load(is)
      }finally{
        is.close
      }
    }
    return body
  }

  def getInputStream = request.getInputStream

  def getLocales: List[String] = {
    val enum = request.getLocales
    val list = new MutableList[String]
    while(enum.hasMoreElements)
      list += enum.nextElement.toString
    return list.toList
  }

  def getAccepts: ContentTypeResolver.ContentType = {
    ContentTypeResolver.contentType(request.getHeader("accept"))
  }

  def getMethod: HTTP.Method = {
    request.getMethod match {
      case "GET" => return HTTP.GET
      case "PUT" => return HTTP.PUT
      case "POST" => return HTTP.POST
      case "DELETE" => return HTTP.DELETE
    }

  }

  def getContentType: Option[String]= {
    val string = request.getContentType
    if(string == null)
      return None
    else return Some(string)
  }
}