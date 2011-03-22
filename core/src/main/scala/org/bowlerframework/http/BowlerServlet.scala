package org.bowlerframework.http

import org.scalatra.ScalatraServlet
import org.scalatra.fileupload.FileUploadSupport
import util.matching.Regex
import javax.servlet.ServletConfig
import org.bowlerframework._
import org.apache.commons.fileupload.FileItem


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