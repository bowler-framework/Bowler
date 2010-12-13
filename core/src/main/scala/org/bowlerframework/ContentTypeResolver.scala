package org.bowlerframework

/**
 * Created by IntelliJ IDEA.
 * User: wfaler
 * Date: 12/12/2010
 * Time: 19:27
 * To change this template use File | Settings | File Templates.
 */

object ContentTypeResolver extends Enumeration{
  type ContentType = Value

  val JSON, XML, HTML = Value

  def contentType(accepts: String): ContentType = {
    val lowerCase = accepts.toLowerCase
    if(lowerCase.contains("text/html") || lowerCase.contains("application/xhtml+xml"))
      return HTML
    else if(lowerCase.contains("application/json") || lowerCase.contains("text/json"))
      return JSON
    else
      return XML
  }

  def contentString(contentType: ContentType): String = {
    if(contentType.equals(HTML))
      return "text/html"
    else if(contentType.equals(JSON))
      return "application/json"
    else if(contentType.equals(XML))
      return "application/xml"
    else
      return "text/html"
  }

}