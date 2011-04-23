package org.bowlerframework

import collection.mutable.HashMap


trait Request {
  def getPath: String

  def getMethod: HttpMethod

  def setMethod(method: HttpMethod)

  def getServerName: String

  def isSecure: Boolean

  def getMappedPath: MappedPath

  def setMappedPath(path: MappedPath)

  def getSession: Session

  def getHeader(name: String): String

  def getHeaders(name: String): List[String]

  def getHeaderNames: List[String]

  def getAccept: String

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

  val scopeDetails = new HashMap[String, Any]


}