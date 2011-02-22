package org.bowlerframework.http

import org.bowlerframework.Response
import javax.servlet.http.HttpServletResponse



class BowlerHttpResponse(response: HttpServletResponse) extends Response{
  var status: Int = 200

  def sendRedirect(location: String) = response.sendRedirect(response.encodeRedirectURL(location))

  def sendError(status: Int) = {response.sendError(status); this.status = status}

  def addHeader(name: String, value: String) = response.addHeader(name, value)

  def setStatus(status: Int) = {response.setStatus(status); this.status = status}

  def setHeader(name: String, value: String) = response.setHeader(name, value)

  def getContentType = response.getContentType

  def setContentType(contentType: String) = response.setContentType(contentType)

  def getOutputStream = response.getOutputStream

  def getWriter = response.getWriter

  def getStatus = status
}