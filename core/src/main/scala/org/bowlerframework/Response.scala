package org.bowlerframework

import java.io.{PrintWriter, OutputStream}

/**
 * Created by IntelliJ IDEA.
 * User: wfaler
 * Date: 13/12/2010
 * Time: 23:57
 * To change this template use File | Settings | File Templates.
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

  /**
   * should encode a redirect url if needed
   */
  def sendRedirect(location: String)
}