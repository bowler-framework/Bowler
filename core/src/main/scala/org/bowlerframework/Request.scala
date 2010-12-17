package org.bowlerframework

import java.io.InputStream
import org.apache.commons.fileupload.FileItem

/**
 * Created by IntelliJ IDEA.
 * User: wfaler
 * Date: 13/12/2010
 * Time: 21:52
 * To change this template use File | Settings | File Templates.
 */

trait Request{
  def getPath: String
  def getMethod: HTTP.Method
  def getServerName: String
  def isSecure: Boolean

  def getSession: Session

  def getHeader(name: String): String
  def getHeaders(name: String): List[String]
  def getHeaderNames: List[String]
  def getAccepts: ContentTypeResolver.ContentType
  def getContentType: Option[String]
  def getLocales: List[String]

  def getParameterNames: Iterable[String]
  def getParameterValues(name: String): List[Any]
  def getParameter(name: String): Any
  def getStringParameter(name: String): String
  def getIntParameter(name: String): Int
  def getLongParameter(name: String): Long
  def getBooleanParameter(name: String): Boolean
  def getParameterMap: Map[String, Any]

  def getRequestBodyAsString: String


}