package org.bowlerframework.controller

import util.matching.Regex
import org.bowlerframework._
import view.scalate.{Layout, TemplateRegistry}
import collection.mutable.MutableList

/**
 * Base Controller trait to be extended. Used to define traits.
 */

trait Controller {
  private val beforeEvents = new MutableList[(Request, Response) => Unit]

  protected final def addBefore(beforeEvent: (Request, Response) => Unit) = beforeEvents += beforeEvent

  protected final def before(request: Request, response: Response){
    beforeEvents.foreach(f => f(request, response))
  }

  def get(routeMatchers: String)(controller: (Request, Response) => Unit) = BowlerConfigurator.get(routeMatchers, new DefaultRouteExecutor({
    (req, resp) => {before(req, resp);controller(req, resp)}
  }, routeMatchers))

  def put(routeMatchers: String)(controller: (Request, Response) => Unit) = BowlerConfigurator.put(routeMatchers, new DefaultRouteExecutor({
    (req, resp) => {before(req, resp);controller(req, resp)}
  }, routeMatchers))

  def post(routeMatchers: String)(controller: (Request, Response) => Unit) = BowlerConfigurator.post(routeMatchers, new DefaultRouteExecutor({
    (req, resp) => {before(req, resp);controller(req, resp)}
  }, routeMatchers))

  def delete(routeMatchers: String)(controller: (Request, Response) => Unit) = BowlerConfigurator.delete(routeMatchers, new DefaultRouteExecutor({
    (req, resp) => {before(req, resp);controller(req, resp)}
  }, routeMatchers))

  def get(routeMatchers: Regex)(controller: (Request, Response) => Unit) = BowlerConfigurator.get(routeMatchers, new DefaultRouteExecutor({
    (req, resp) => {before(req, resp);controller(req, resp)}
  }, routeMatchers.toString, true))

  def put(routeMatchers: Regex)(controller: (Request, Response) => Unit) = BowlerConfigurator.put(routeMatchers, new DefaultRouteExecutor({
    (req, resp) => {before(req, resp);controller(req, resp)}
  }, routeMatchers.toString, true))

  def post(routeMatchers: Regex)(controller: (Request, Response) => Unit) = BowlerConfigurator.post(routeMatchers, new DefaultRouteExecutor({
    (req, resp) => {before(req, resp);controller(req, resp)}
  }, routeMatchers.toString, true))

  def delete(routeMatchers: Regex)(controller: (Request, Response) => Unit) = BowlerConfigurator.delete(routeMatchers, new DefaultRouteExecutor({
    (req, resp) => {before(req, resp);controller(req, resp)}
  }, routeMatchers.toString, true))

}

final class DefaultRouteExecutor(controller: (Request, Response) => Unit, routeMatcher: String, isRegex: Boolean = false) extends RouteExecutor {
  def executeRoute(requestScope: RequestScope) {
    RequestScope.executeRoute(MappedPath(routeMatcher, isRegex), requestScope, controller)
  }
}