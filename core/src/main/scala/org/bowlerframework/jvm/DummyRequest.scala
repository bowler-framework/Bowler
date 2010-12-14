package org.bowlerframework.jvm

import org.bowlerframework.Request

/**
 * Created by IntelliJ IDEA.
 * User: wfaler
 * Date: 14/12/2010
 * Time: 00:14
 * To change this template use File | Settings | File Templates.
 */

class DummyRequest extends Request{

  def getRequestBodyAsString = null

  def getBooleanParameter(name: String) = false

  def getLongParameter(name: String) = 1l

  def getIntParameter(name: String) = 1

  def getStringParameter(name: String) = null

  def getParameter(name: String) = null

  def getParameterValues(name: String) = List(1,2)

  def getParameterNames = List("hello")

  def getLocales = List("en_US")

  def getAcceptsContentType = null

  def getHeaderNames = List("accept")

  def getHeaders(name: String) = List("val")

  def getHeader(name: String) = null

  def getSession = null

  def isSecure = false

  def getServerName = "localhost"

  def getPath = null
}