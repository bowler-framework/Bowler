package org.bowlerframework.http

import org.scalatra.ScalatraServlet
import org.scalatra.fileupload.FileUploadSupport
import org.bowlerframework.{HTTP, BowlerConfigurator, RequestScope, RouteExecutor}
import util.matching.Regex
import javax.servlet.ServletConfig
import org.bowlerframework.controller.Controller

/**
 * Created by IntelliJ IDEA.
 * User: wfaler
 * Date: 04/01/2011
 * Time: 23:51
 * To change this layout use File | Settings | File Templates.
 */

class BowlerServlet extends ScalatraServlet with FileUploadSupport with BowlerHttpApplicationRouter{
  var bootstrap: AnyRef = null

  override def init(config: ServletConfig): Unit = {
    super.init(config)
    BowlerConfigurator.setApplicationRouter(this)
    BowlerConfigurator.isServletApp = true

    if(config.getInitParameter("bootstrapClass") != null) {
      bootstrap = Class.forName(config.getInitParameter("bootstrapClass")).newInstance.asInstanceOf[AnyRef]
    }
  }

  def addApplicationRoute(protocol: HTTP.Method, routeMatchers: String, routeExecutor: RouteExecutor) = {
      protocol.toString match {
        case "GET" => this.get(routeMatchers){mapExecutor(routeExecutor)}
        case "PUT" => this.put(routeMatchers){mapExecutor(routeExecutor)}
        case "POST" => this.post(routeMatchers){mapExecutor(routeExecutor)}
        case "DELETE" => this.delete(routeMatchers){mapExecutor(routeExecutor)}
      }
  }


  def addApplicationRoute(protocol: HTTP.Method, routeMatchers: Regex, routeExecutor: RouteExecutor) = {
      protocol.toString match {
        case "GET" => this.get(routeMatchers){mapExecutor(routeExecutor)}
        case "PUT" => this.put(routeMatchers){mapExecutor(routeExecutor)}
        case "POST" => this.post(routeMatchers){mapExecutor(routeExecutor)}
        case "DELETE" => this.delete(routeMatchers){mapExecutor(routeExecutor)}
      }
  }

  private def mapExecutor(routeExecutor: RouteExecutor): Any = {
    val bowlerRequest = new BowlerHttpRequest(this.requestPath, this.request, this.flattenParameters(this.request, this.params, this.multiParams, this.fileParams, this.fileMultiParams))
    val scope = RequestScope(bowlerRequest, new BowlerHttpResponse(this.response))
    routeExecutor.executeRoute(scope)
  }
}