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

  def getHeader(name: String): Option[String]

  def getHeaders(name: String): List[String]

  def getHeaderNames: List[String]

  def getAccept: Option[String]

  def getContentType: Option[String]

  def getLocales: List[String]

  def getParameterNames: Iterable[String]

  def getParameterValues(name: String): Option[List[Any]]

  def getParameter(name: String): Option[Any]

  def getStringParameter(name: String): Option[String]

  def getIntParameter(name: String): Option[Int]

  def getLongParameter(name: String): Option[Long]

  def getBooleanParameter(name: String): Option[Boolean]

  def getParameterMap: Map[String, Any]

  def getRequestBodyAsString: String

  val scopeDetails = new HashMap[String, Any]


}