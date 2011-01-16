package org.bowlerframework

import util.matching.Regex

/**
 * Abstraction for base responder for routes. Implemented by the BowlerServlet and BowlerFilter
 */

trait ApplicationRouter{
  def addApplicationRoute(protocol: HTTP.Method, routeMatchers: String, routeExecutor: RouteExecutor)
  def addApplicationRoute(protocol: HTTP.Method, routeMatchers: Regex, routeExecutor: RouteExecutor)

}