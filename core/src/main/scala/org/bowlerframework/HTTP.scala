package org.bowlerframework

import http.BowlerHttpRequest

/**
 * Created by IntelliJ IDEA.
 * User: wfaler
 * Date: 16/12/2010
 * Time: 22:28
 * To change this layout use File | Settings | File Templates.
 */

object HTTP extends Enumeration{
  type Method = Value

  val GET, POST, PUT, DELETE = Value

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

  def relativeUrl(uri: String): String = {
    if(uri.startsWith("/"))
      return basePath + uri.substring(1)
    else
      return basePath + uri
  }

}