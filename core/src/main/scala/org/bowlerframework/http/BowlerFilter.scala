package org.bowlerframework.http

import org.scalatra.ScalatraFilter
import javax.servlet.FilterConfig
import org.scalatra.fileupload.FileUploadSupport
import util.matching.Regex
import org.bowlerframework._

class BowlerFilter extends ScalatraFilter with FileUploadSupport with BowlerHttpApplicationRouter{
  private var application: BowlerApplication = null

  override def initialize(config: FilterConfig): Unit = {
    super.initialize(config)
    BowlerConfigurator.setApplicationRouter(this)
    application = Class.forName(config.getInitParameter("applicationClass")).newInstance.asInstanceOf[BowlerApplication]

  }

  def addApplicationRoute(protocol: String, routeMatchers: String, routeExecutor: RouteExecutor) = {
      protocol match {
        case "GET" => this.get(routeMatchers){mapExecutor(routeExecutor)}
        case "PUT" => this.put(routeMatchers){mapExecutor(routeExecutor)}
        case "POST" => this.post(routeMatchers){mapExecutor(routeExecutor)}
        case "DELETE" => this.delete(routeMatchers){mapExecutor(routeExecutor)}
      }
  }


  def addApplicationRoute(protocol: String, routeMatchers: Regex, routeExecutor: RouteExecutor) = {
      protocol match {
        case "GET" => this.get(routeMatchers){mapExecutor(routeExecutor)}
        case "PUT" => this.put(routeMatchers){mapExecutor(routeExecutor)}
        case "POST" => this.post(routeMatchers){mapExecutor(routeExecutor)}
        case "DELETE" => this.delete(routeMatchers){mapExecutor(routeExecutor)}
      }
  }

  private def mapExecutor(routeExecutor: RouteExecutor): Any = {
    val scope = RequestScope(this.request, this.response, new BowlerHttpSession(this.request.getSession(true)),
      this.flattenParameters(this.request, this.params, this.multiParams, this.fileParams, this.fileMultiParams))

    println("THIS PATH: " + this.requestPath)

    routeExecutor.executeRoute(scope)
  }

}