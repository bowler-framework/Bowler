package org.bowlerframework

import util.matching.Regex

/**
 * Created by IntelliJ IDEA.
 * User: wfaler
 * Date: Dec 10, 2010
 * Time: 2:49:20 AM
 * To change this template use File | Settings | File Templates.
 */

trait ApplicationRouter{
  def addApplicationRoute(protocol: String, routeMatchers: String, routeExecutor: RouteExecutor)
  def addApplicationRoute(protocol: String, routeMatchers: Regex, routeExecutor: RouteExecutor)

}