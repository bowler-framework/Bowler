package org.bowlerframework.view

/**
 * Created by IntelliJ IDEA.
 * User: wfaler
 * Date: 19/03/2011
 * Time: 00:19
 * To change this template use File | Settings | File Templates.
 */

object Accept{
  def matchAccept(acceptHeader: String): Accept = {
    if(acceptHeader == null)
      return HTML
    val lowerCase = acceptHeader.toLowerCase
    if(lowerCase.contains("text/html") || lowerCase.contains("application/xhtml+xml"))
      return HTML
    else if(lowerCase.contains("application/json") || lowerCase.contains("text/json"))
      return JSON
    else if(lowerCase.contains("application/xml") || lowerCase.contains("text/xml"))
      return XML
    else
      return HTML
  }
}

sealed trait Accept
case object HTML extends Accept
case object JSON extends Accept
case object XML extends Accept