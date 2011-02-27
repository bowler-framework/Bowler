package org.bowlerframework

import http.BowlerHttpRequest

/**
 * Useful HTTP utilities.
 */

object HTTP{

  def basePath: String = {
  	RequestScope.request match {
      case request:BowlerHttpRequest =>
        BowlerConfigurator.isServletApp match {
          case true  => appendSlash(request.request.getContextPath + request.request.getServletPath)
          case _ => request.request.getContextPath + "/"
        }
      case _ => "/"
    }
  }

  private def appendSlash(path: String): String = if(!path.endsWith("/"))  path + "/" else path

  /**
   * maps an app uri to a perfect relative URI, for instance if the app and/or servlet context is /app, and the uri within the app is
   * /myUri, this will return /app/myUri, given /myUri as an arg.
   */
  def relativeUrl(uri: String): String = {
    if(uri.startsWith("/"))
      return basePath + uri.substring(1)
    else
      return basePath + uri
  }

}

sealed trait HttpMethod

case object GET extends HttpMethod
case object POST extends HttpMethod
case object PUT extends HttpMethod
case object DELETE extends HttpMethod
