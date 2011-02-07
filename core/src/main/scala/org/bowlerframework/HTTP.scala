package org.bowlerframework

import http.BowlerHttpRequest

/**
 * Useful HTTP utilities.
 */

object HTTP{

  def basePath: String = {
    if(RequestScope.request.isInstanceOf[BowlerHttpRequest] && BowlerConfigurator.isServletApp){
      val request = RequestScope.request.asInstanceOf[BowlerHttpRequest]
      var path = request.request.getContextPath + request.request.getServletPath
      if(!path.endsWith("/")) path = path + "/"
      return path
    }else if(RequestScope.request.isInstanceOf[BowlerHttpRequest] ){
      val request = RequestScope.request.asInstanceOf[BowlerHttpRequest]
      return request.request.getContextPath + "/"
    }else
      return "/"
  }

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
