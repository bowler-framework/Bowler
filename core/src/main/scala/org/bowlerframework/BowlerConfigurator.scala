package org.bowlerframework


import util.matching.Regex
import java.util.Enumeration
import java.net.URL

/**
 * Created by IntelliJ IDEA.
 * User: wfaler
 * Date: Dec 9, 2010
 * Time: 11:30:58 PM
 * To change this template use File | Settings | File Templates.
 */

object BowlerConfigurator extends ApplicationRouter{
  private var router: ApplicationRouter = null

  def setApplicationRouter(router: ApplicationRouter) = {
    this.router = router
  }


  def addApplicationRoute(protocol: HTTP.Method, routeMatchers: String, routeExecutor: RouteExecutor) = router.addApplicationRoute(protocol, routeMatchers, routeExecutor)

  def addApplicationRoute(protocol: HTTP.Method, routeMatchers: Regex, routeExecutor: RouteExecutor) = router.addApplicationRoute(protocol, routeMatchers, routeExecutor)

  def get(routeMatchers: String,routeExecutor: RouteExecutor) = router.addApplicationRoute(HTTP.GET, routeMatchers,routeExecutor)
  def put(routeMatchers: String,routeExecutor: RouteExecutor) = router.addApplicationRoute(HTTP.PUT, routeMatchers,routeExecutor)
  def post(routeMatchers: String,routeExecutor: RouteExecutor) = router.addApplicationRoute(HTTP.POST, routeMatchers,routeExecutor)
  def delete(routeMatchers: String,routeExecutor: RouteExecutor) = router.addApplicationRoute(HTTP.DELETE, routeMatchers,routeExecutor)

  def get(routeMatchers: Regex,routeExecutor: RouteExecutor) = router.addApplicationRoute(HTTP.GET, routeMatchers,routeExecutor)
  def put(routeMatchers: Regex,routeExecutor: RouteExecutor) = router.addApplicationRoute(HTTP.PUT, routeMatchers,routeExecutor)
  def post(routeMatchers: Regex,routeExecutor: RouteExecutor) = router.addApplicationRoute(HTTP.POST, routeMatchers,routeExecutor)
  def delete(routeMatchers: Regex,routeExecutor: RouteExecutor) = router.addApplicationRoute(HTTP.DELETE, routeMatchers,routeExecutor)

}