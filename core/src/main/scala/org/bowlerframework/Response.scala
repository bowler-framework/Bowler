package org.bowlerframework

import java.io.{PrintWriter, OutputStream}

/**
 * Analogous to HTTPServletResponse, but used to indirect from Servlet API (other HTTP environments and testing).
 */

trait Response{
  def getWriter: PrintWriter
  def getOutputStream: OutputStream

  def setContentType(contentType: String)
  def getContentType: String

  def setHeader(name: String, value: String)
  def addHeader(name: String, value: String)
  def setStatus(status: Int)
  def sendError(status: Int)
  def getStatus: Int

  /**
   * should encode a redirect url if needed
   */
  def sendRedirect(location: String)
}