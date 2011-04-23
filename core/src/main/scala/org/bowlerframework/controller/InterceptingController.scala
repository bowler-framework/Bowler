package org.bowlerframework.controller

import util.matching.Regex
import org.bowlerframework.{BowlerConfigurator, Request, Response}

/**
 * Controller type that allows you to define a function to be used "around" request-response cycles. Useful for instance
 * in having transactional tx.start, tx.commit and tx.rollback type functionality.
 */

trait InterceptingController extends Controller {
  def around(request: Request, response: Response)(controller: (Request, Response) => Unit): Unit

  override def get(routeMatchers: String)(controller: (Request, Response) => Unit) =
    BowlerConfigurator.get(routeMatchers, new DefaultRouteExecutor({
      (req, resp) => {before(req,resp);around(req, resp)(controller)}
    }, routeMatchers))

  override def put(routeMatchers: String)(controller: (Request, Response) => Unit) = BowlerConfigurator.put(routeMatchers, new DefaultRouteExecutor({
    (req, resp) => {before(req,resp);around(req, resp)(controller)}
  }, routeMatchers))

  override def post(routeMatchers: String)(controller: (Request, Response) => Unit) = BowlerConfigurator.post(routeMatchers, new DefaultRouteExecutor({
    (req, resp) => {before(req,resp);around(req, resp)(controller)}
  }, routeMatchers))

  override def delete(routeMatchers: String)(controller: (Request, Response) => Unit) = BowlerConfigurator.delete(routeMatchers, new DefaultRouteExecutor({
    (req, resp) => {before(req,resp);around(req, resp)(controller)}
  }, routeMatchers))

  override def get(routeMatchers: Regex)(controller: (Request, Response) => Unit) = BowlerConfigurator.get(routeMatchers, new DefaultRouteExecutor({
    (req, resp) => {before(req,resp);around(req, resp)(controller)}
  }, routeMatchers.toString, true))

  override def put(routeMatchers: Regex)(controller: (Request, Response) => Unit) = BowlerConfigurator.put(routeMatchers, new DefaultRouteExecutor({
    (req, resp) => {before(req,resp);around(req, resp)(controller)}
  }, routeMatchers.toString, true))

  override def post(routeMatchers: Regex)(controller: (Request, Response) => Unit) = BowlerConfigurator.post(routeMatchers, new DefaultRouteExecutor({
    (req, resp) => {before(req,resp);around(req, resp)(controller)}
  }, routeMatchers.toString, true))

  override def delete(routeMatchers: Regex)(controller: (Request, Response) => Unit) = BowlerConfigurator.delete(routeMatchers, new DefaultRouteExecutor({
    (req, resp) => {before(req,resp);around(req, resp)(controller)}
  }, routeMatchers.toString, true))
}