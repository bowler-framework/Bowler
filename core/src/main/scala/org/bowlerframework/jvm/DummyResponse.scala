package org.bowlerframework.jvm

import org.bowlerframework.Response
import java.util.HashMap
import java.io.{OutputStream, PrintWriter, StringWriter}



class DummyResponse(val stringWriter: StringWriter = new StringWriter) extends Response{
  private var status = 200
  private val headers = new HashMap[String, String]

  private var contentType = "text/html"
  private val writer = new PrintWriter(stringWriter)

  private var outputSteam: OutputStream = null

  def sendError(status: Int) = {this.status = status}

  def setStatus(status: Int) = {this.status = status}

  def setHeader(name: String, value: String) = headers.put(name, value)

  def getContentType = contentType

  def setContentType(contentType: String) = {this.contentType = contentType}

  def getOutputStream = outputSteam

  def setOutPutStream(output: OutputStream) = {outputSteam = output}

  def getWriter = writer

  def addHeader(name: String, value: String) = headers.put(name, value)

  // TODO implement
  def sendRedirect(location: String) = null

  override def toString = stringWriter.toString

  def getStatus = status


}