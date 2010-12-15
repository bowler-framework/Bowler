package org.bowlerframework.controller

import util.matching.Regex
import org.bowlerframework._

/**
 * Created by IntelliJ IDEA.
 * User: wfaler
 * Date: 15/12/2010
 * Time: 19:17
 * To change this template use File | Settings | File Templates.
 */

trait Controller {

  def get(routeMatchers: String)(controller: => Unit) = BowlerConfigurator.get(routeMatchers, new DefaultRouteExecutor(controller,routeMatchers))

  def put(routeMatchers: String)(controller: => Unit) = BowlerConfigurator.put(routeMatchers, new DefaultRouteExecutor(controller, routeMatchers))

  def post(routeMatchers: String)(controller: => Unit) = BowlerConfigurator.post(routeMatchers, new DefaultRouteExecutor(controller, routeMatchers))

  def delete(routeMatchers: String)(controller: => Unit) = BowlerConfigurator.delete(routeMatchers, new DefaultRouteExecutor(controller, routeMatchers))

  def get(routeMatchers: Regex)(controller: => Unit) = BowlerConfigurator.get(routeMatchers, new DefaultRouteExecutor(controller, routeMatchers.toString, true))

  def put(routeMatchers: Regex)(controller: => Unit) = BowlerConfigurator.put(routeMatchers, new DefaultRouteExecutor(controller, routeMatchers.toString, true))

  def post(routeMatchers: Regex)(controller: => Unit) = BowlerConfigurator.post(routeMatchers, new DefaultRouteExecutor(controller, routeMatchers.toString, true))

  def delete(routeMatchers: Regex)(controller: => Unit) = BowlerConfigurator.delete(routeMatchers, new DefaultRouteExecutor(controller, routeMatchers.toString, true))

}

class DefaultRouteExecutor(controller: => Unit, routeMatcher: String, isRegex: Boolean = false) extends RouteExecutor {
  def executeRoute(requestScope: RequestScope){
    RequestScope.executeRoute(MappedPath(routeMatcher, isRegex), requestScope, controller)
  }
}