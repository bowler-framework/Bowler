package org.bowlerframework.http

import org.scalatra.ScalatraFilter
import javax.servlet.FilterConfig
import org.scalatra.fileupload.FileUploadSupport
import org.apache.commons.fileupload.FileItem
import util.matching.Regex
import org.bowlerframework._

class BowlerFilter extends ScalatraFilter with FileUploadSupport with BowlerHttpApplicationRouter{

  var bootstrap: AnyRef = null

  override def initialize(config: FilterConfig): Unit = {
    super.initialize(config)
    BowlerConfigurator.setApplicationRouter(this)
    BowlerConfigurator.isServletApp = false
    println(config.getServletContext.getRealPath("WEB-INF"))

    if(config.getInitParameter("bootstrapClass") != null) {
      bootstrap = Class.forName(config.getInitParameter("bootstrapClass")).newInstance.asInstanceOf[AnyRef]
    }
  }

  def addApplicationRoute(protocol: HttpMethod, routeMatchers: String, routeExecutor: RouteExecutor) = {
      protocol match {
        case GET => this.get(routeMatchers){mapExecutor(routeExecutor)}
        case PUT => this.put(routeMatchers){mapExecutor(routeExecutor)}
        case POST => this.post(routeMatchers){mapExecutor(routeExecutor)}
        case DELETE => this.delete(routeMatchers){mapExecutor(routeExecutor)}
      }
  }


  def addApplicationRoute(protocol: HttpMethod, routeMatchers: Regex, routeExecutor: RouteExecutor) = {
      protocol match {
        case GET => this.get(routeMatchers){mapExecutor(routeExecutor)}
        case PUT => this.put(routeMatchers){mapExecutor(routeExecutor)}
        case POST => this.post(routeMatchers){mapExecutor(routeExecutor)}
        case DELETE => this.delete(routeMatchers){mapExecutor(routeExecutor)}
      }
  }

  override def requestPath = if (request.getPathInfo != null) request.getPathInfo else request.getServletPath

  private def mapExecutor(routeExecutor: RouteExecutor): Any = {
    var files: collection.Map[String, FileItem] = Map[String, FileItem]()
    var listFiles: collection.Map[String, Seq[FileItem]] = Map[String, Seq[FileItem]]()

    try{
      files = this.fileParams
    }catch{
      case e: Exception => {}
    }

    try{
      listFiles = this.fileMultiParams
    }catch{
      case e: Exception => {}
    }

    val bowlerRequest = new BowlerHttpRequest(this.requestPath, this.request, this.flattenParameters(this.request, this.params, this.multiParams, files, listFiles))
    val scope = RequestScope(bowlerRequest, new BowlerHttpResponse(this.response))
    routeExecutor.executeRoute(scope)
  }

}
