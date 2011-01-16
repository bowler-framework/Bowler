package org.bowlerframework.controller

import util.matching.Regex
import org.bowlerframework._

/**
 * Base Controller trait to be extended. Used to define traits.
 */

trait Controller {

  def get(routeMatchers: String)(controller: (Request, Response) => Unit) = BowlerConfigurator.get(routeMatchers, new DefaultRouteExecutor(controller,routeMatchers))

  def put(routeMatchers: String)(controller: (Request, Response) => Unit) = BowlerConfigurator.put(routeMatchers, new DefaultRouteExecutor(controller, routeMatchers))

  def post(routeMatchers: String)(controller: (Request, Response) => Unit) = BowlerConfigurator.post(routeMatchers, new DefaultRouteExecutor(controller, routeMatchers))

  def delete(routeMatchers: String)(controller: (Request, Response) => Unit) = BowlerConfigurator.delete(routeMatchers, new DefaultRouteExecutor(controller, routeMatchers))

  def get(routeMatchers: Regex)(controller: (Request, Response) => Unit) = BowlerConfigurator.get(routeMatchers, new DefaultRouteExecutor(controller, routeMatchers.toString, true))

  def put(routeMatchers: Regex)(controller: (Request, Response) => Unit) = BowlerConfigurator.put(routeMatchers, new DefaultRouteExecutor(controller, routeMatchers.toString, true))

  def post(routeMatchers: Regex)(controller: (Request, Response) => Unit) = BowlerConfigurator.post(routeMatchers, new DefaultRouteExecutor(controller, routeMatchers.toString, true))

  def delete(routeMatchers: Regex)(controller: (Request, Response) => Unit) = BowlerConfigurator.delete(routeMatchers, new DefaultRouteExecutor(controller, routeMatchers.toString, true))

}

class DefaultRouteExecutor(controller: (Request, Response) => Unit, routeMatcher: String, isRegex: Boolean = false) extends RouteExecutor {
  def executeRoute(requestScope: RequestScope){
    RequestScope.executeRoute(MappedPath(routeMatcher, isRegex), requestScope, controller)
  }
}