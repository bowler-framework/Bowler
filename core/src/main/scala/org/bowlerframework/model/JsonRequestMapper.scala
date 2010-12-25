package org.bowlerframework.model

import org.bowlerframework.{HTTP, Request}

/**
 * Created by IntelliJ IDEA.
 * User: wfaler
 * Date: 21/12/2010
 * Time: 22:13
 * To change this template use File | Settings | File Templates.
 */

class JsonRequestMapper extends RequestMapper{
  def getValue[T](request: Request, nameHint: String)(implicit m: Manifest[T]): T = {
    if(request.getMethod == HTTP.GET || request.getMethod == HTTP.DELETE){
      val mapper = new DefaultRequestMapper
      return mapper.getValue[T](request, nameHint)(m)
    }else
      return None.asInstanceOf[T]
  }
}