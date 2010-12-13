package org.bowlerframework

import org.scalatra.{PathPatternParser, RouteMatcher, ScalatraKernel}
import collection.mutable.{HashMap, ListBuffer, MutableList}
import org.scalatra.RouteMatcher._
import ScalatraKernel.MultiParams
import util.matching.Regex

/**
 * Created by IntelliJ IDEA.
 * User: wfaler
 * Date: Dec 9, 2010
 * Time: 11:30:58 PM
 * To change this template use File | Settings | File Templates.
 */

object BowlerConfigurator{
  private var router: ApplicationRouter = null

  def setApplicationRouter(router: ApplicationRouter) = {
    this.router = router
  }

  def get(routeMatchers: String,routeExecutor: RouteExecutor) = router.addApplicationRoute("GET", routeMatchers,routeExecutor)
  def put(routeMatchers: String,routeExecutor: RouteExecutor) = router.addApplicationRoute("PUT", routeMatchers,routeExecutor)
  def post(routeMatchers: String,routeExecutor: RouteExecutor) = router.addApplicationRoute("POST", routeMatchers,routeExecutor)
  def delete(routeMatchers: String,routeExecutor: RouteExecutor) = router.addApplicationRoute("DELETE", routeMatchers,routeExecutor)

  def get(routeMatchers: Regex,routeExecutor: RouteExecutor) = router.addApplicationRoute("GET", routeMatchers,routeExecutor)
  def put(routeMatchers: Regex,routeExecutor: RouteExecutor) = router.addApplicationRoute("PUT", routeMatchers,routeExecutor)
  def post(routeMatchers: Regex,routeExecutor: RouteExecutor) = router.addApplicationRoute("POST", routeMatchers,routeExecutor)
  def delete(routeMatchers: Regex,routeExecutor: RouteExecutor) = router.addApplicationRoute("DELETE", routeMatchers,routeExecutor)

}