package org.bowlerframework.controller

import util.matching.Regex
import org.bowlerframework.{RequestCycleAware, RequestScope, RouteExecutor}
import collection.mutable.HashMap

/**
 * Created by IntelliJ IDEA.
 * User: wfaler
 * Date: 12/12/2010
 * Time: 22:55
 * To change this template use File | Settings | File Templates.
 */

trait RequestCycleAwareRouteExecutor extends RouteExecutor with RequestCycleAware{
  def executeRoute(requestScope: RequestScope) = {
    val requestCycle = this.newRequestCycle(requestScope)
    try{
      requestCycle.onBefore
      executeController(requestScope)
    }catch{
      case e: Exception => requestCycle.onError(e)
    }finally{
      requestCycle.onAfter
    }
  }



  def executeController(scope: RequestScope): Any
}

class RouteExecutor1[T](func: T => Any)(implicit m: Manifest[T]) extends RequestCycleAwareRouteExecutor{

  def executeController(scope: RequestScope) = {

  }
}