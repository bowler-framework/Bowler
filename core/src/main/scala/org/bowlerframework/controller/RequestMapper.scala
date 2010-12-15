package org.bowlerframework.controller

import org.bowlerframework.RequestScope

/**
 * Created by IntelliJ IDEA.
 * User: wfaler
 * Date: 12/12/2010
 * Time: 23:42
 * To change this template use File | Settings | File Templates.
 */

trait RequestMapper{
  def mapRequest[T](func: T => Any)(implicit m: Manifest[T]): Any ={
    val request = RequestScope.request
    return None.asInstanceOf[T]
  }
}