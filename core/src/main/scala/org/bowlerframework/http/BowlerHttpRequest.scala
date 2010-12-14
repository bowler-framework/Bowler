package org.bowlerframework.http

import javax.servlet.http.HttpServletRequest
import collection.mutable.MutableList
import com.recursivity.commons.bean._
import com.recursivity.commons.StringInputStreamReader
import org.apache.commons.fileupload.FileItem
import org.bowlerframework.{ContentTypeResolver, Request}

/**
 * Created by IntelliJ IDEA.
 * User: wfaler
 * Date: 13/12/2010
 * Time: 21:57
 * To change this template use File | Settings | File Templates.
 */

class BowlerHttpRequest(path: String, request: HttpServletRequest, params: Map[String, Any]) extends Request with StringInputStreamReader{
  val session = new BowlerHttpSession(request.getSession(true))

  private val intTransformer = new JavaIntegerTransformer
  private val longTransformer = new LongTransformer
  private val booleanTransformer = new JavaBooleanTransformer

  def isSecure = request.isSecure

  def getServerName = request.getServerName

  def getBooleanParameter(name: String) = booleanTransformer.toValue(params(name).toString).asInstanceOf[Boolean]

  def getLongParameter(name: String) = longTransformer.toValue(params(name).toString).asInstanceOf[Long]

  def getIntParameter(name: String) = intTransformer.toValue(params(name).toString).asInstanceOf[Int]

  def getParameter(name: String) = params(name)

  def getParameterValues(name: String) = params(name).asInstanceOf[List[Any]]

  def getParameterNames = params.keys


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
    val is = this.getInputStream
    try{
      return load(is)
    }finally{
      is.close
    }
  }

  def getInputStream = request.getInputStream

  def getLocales: List[String] = {
    val enum = request.getLocales
    val list = new MutableList[String]
    while(enum.hasMoreElements)
      list += enum.nextElement.toString
    return list.toList
  }

  def getAcceptsContentType: ContentTypeResolver.ContentType = {
    println("Content-Type: " + request.getHeader("accept"))
    ContentTypeResolver.contentType(request.getHeader("accept"))
  }

}