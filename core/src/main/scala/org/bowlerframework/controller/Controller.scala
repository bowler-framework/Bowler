package org.bowlerframework.controller

import util.matching.Regex
import util.DynamicVariable
import org.bowlerframework._

/**
 * Created by IntelliJ IDEA.
 * User: wfaler
 * Date: 15/12/2010
 * Time: 19:17
 * To change this template use File | Settings | File Templates.
 */

trait Controller {

  def get(routeMatchers: String)(controller: => Unit) = BowlerConfigurator.get(routeMatchers, new DefaultRouteExecutor(routeMatchers, controller))

  def put(routeMatchers: String)(controller: => Unit) = BowlerConfigurator.put(routeMatchers, new DefaultRouteExecutor(routeMatchers, controller))

  def post(routeMatchers: String)(controller: => Unit) = BowlerConfigurator.post(routeMatchers, new DefaultRouteExecutor(routeMatchers, controller))

  def delete(routeMatchers: String)(controller: => Unit) = BowlerConfigurator.delete(routeMatchers, new DefaultRouteExecutor(routeMatchers, controller))

  def get(routeMatchers: Regex)(controller: => Unit) = BowlerConfigurator.get(routeMatchers, new DefaultRouteExecutor(null, controller))

  def put(routeMatchers: Regex)(controller: => Unit) = BowlerConfigurator.put(routeMatchers, new DefaultRouteExecutor(null, controller))

  def post(routeMatchers: Regex)(controller: => Unit) = BowlerConfigurator.post(routeMatchers, new DefaultRouteExecutor(null, controller))

  def delete(routeMatchers: Regex)(controller: => Unit) = BowlerConfigurator.delete(routeMatchers, new DefaultRouteExecutor(null, controller))

}

class DefaultRouteExecutor(routeMatcher: String, controller: => Unit) extends RouteExecutor {
  def executeRoute(requestScope: RequestScope){
    RequestScope.executeRoute(null, requestScope, controller)
  }
}