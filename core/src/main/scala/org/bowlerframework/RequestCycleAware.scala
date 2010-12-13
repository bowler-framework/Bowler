package org.bowlerframework

/**
 * Created by IntelliJ IDEA.
 * User: wfaler
 * Date: 12/12/2010
 * Time: 23:17
 * To change this template use File | Settings | File Templates.
 */

trait RequestCycleAware{
  private var requestCycle: Class[_<:RequestCycle] = classOf[DefaultRequestCycle]
  def setRequestCycle(cycle: Class[_<:RequestCycle]) = (requestCycle = cycle)

  def newRequestCycle(scope: RequestScope): RequestCycle ={
    val cycle = requestCycle.newInstance.asInstanceOf[RequestCycle]
    cycle.init(scope)
    return cycle
  }


}