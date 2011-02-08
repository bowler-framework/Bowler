package org.bowlerframework

/**
 * Created by IntelliJ IDEA.
 * User: wfaler
 * Date: 08/02/2011
 * Time: 23:51
 * To change this template use File | Settings | File Templates.
 */

object ContentTypeResolver extends Enumeration{
  type ContentType = Value

  val JSON, XML, HTML = Value

  def contentType(accept: String): ContentType = {
    if(accept == null)
      return HTML
    val lowerCase = accept.toLowerCase
    if(lowerCase.contains("text/html") || lowerCase.contains("application/xhtml+xml"))
      return HTML
    else if(lowerCase.contains("application/json") || lowerCase.contains("text/json"))
      return JSON
    else
      return XML
  }

  def contentString(contentType: ContentType): String = {
    contentType match{
      case HTML => return "text/html"
      case JSON => return "application/json"
      case XML => return "application/xml"
      case _ => return "text/html"
    }
  }

}