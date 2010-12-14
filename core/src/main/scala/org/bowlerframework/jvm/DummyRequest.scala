package org.bowlerframework.jvm

import org.bowlerframework.{Session, Request}
import com.recursivity.commons.bean.{JavaIntegerTransformer, LongTransformer, JavaBooleanTransformer}

/**
 * Created by IntelliJ IDEA.
 * User: wfaler
 * Date: 14/12/2010
 * Time: 00:14
 * To change this template use File | Settings | File Templates.
 */

class DummyRequest(path: String, params: Map[String, Any], headers: Map[String, String], body: String, session: Session = new DummySession) extends Request{
  private val intTransformer = new JavaIntegerTransformer
  private val longTransformer = new LongTransformer
  private val booleanTransformer = new JavaBooleanTransformer

  def getRequestBodyAsString = body

  def getBooleanParameter(name: String) = booleanTransformer.toValue(params(name).toString).asInstanceOf[Boolean]

  def getLongParameter(name: String) = longTransformer.toValue(params(name).toString).asInstanceOf[Long]

  def getIntParameter(name: String) = intTransformer.toValue(params(name).toString).asInstanceOf[Int]

  def getParameter(name: String) = params(name)

  def getParameterValues(name: String) = params(name).asInstanceOf[List[Any]]

  def getParameterNames = params.keys

  def getStringParameter(name: String) = params(name).toString

  def getSession = session

  def isSecure = false

  def getServerName = "localhost"

  def getPath = path

  def getHeaders(name: String) = List(headers(name))

  def getHeader(name: String) = headers(name)



  def getLocales = List("en_US")

  def getAcceptsContentType = null

  def getHeaderNames = List("accept")


}