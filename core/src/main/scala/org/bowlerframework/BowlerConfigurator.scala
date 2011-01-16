package org.bowlerframework


import util.matching.Regex
import view.{ViewRenderer, DefaultRenderStrategy, RenderStrategy}
import reflect.BeanProperty

/**
 * Kind of the "hub"/centre point for all things configuration within Bowler.
 */

object BowlerConfigurator extends ApplicationRouter {

  @BeanProperty
  var isServletApp = true

  private var requestMappingStrategy: RequestMappingStrategy = new DefaultRequestMappingStrategy

  private var renderStrategy: RenderStrategy = new DefaultRenderStrategy

  private var router: ApplicationRouter = null

  def getRequestMapper(request: Request) = requestMappingStrategy.getRequestMapper(request)

  def setApplicationRouter(router: ApplicationRouter) = {this.router = router}

  def resolveViewRenderer(request: Request): ViewRenderer = renderStrategy.resolveViewRenderer(request)

  def setRenderStrategy(renderStrategy: RenderStrategy) = {this.renderStrategy = renderStrategy}

  def setRequestMappingStrategy(mappingStrategy: RequestMappingStrategy) = {this.requestMappingStrategy = mappingStrategy}


  def addApplicationRoute(protocol: HTTP.Method, routeMatchers: String, routeExecutor: RouteExecutor) = router.addApplicationRoute(protocol, routeMatchers, routeExecutor)

  def addApplicationRoute(protocol: HTTP.Method, routeMatchers: Regex, routeExecutor: RouteExecutor) = router.addApplicationRoute(protocol, routeMatchers, routeExecutor)

  def get(routeMatchers: String, routeExecutor: RouteExecutor) = router.addApplicationRoute(HTTP.GET, routeMatchers, routeExecutor)

  def put(routeMatchers: String, routeExecutor: RouteExecutor) = router.addApplicationRoute(HTTP.PUT, routeMatchers, routeExecutor)

  def post(routeMatchers: String, routeExecutor: RouteExecutor) = router.addApplicationRoute(HTTP.POST, routeMatchers, routeExecutor)

  def delete(routeMatchers: String, routeExecutor: RouteExecutor) = router.addApplicationRoute(HTTP.DELETE, routeMatchers, routeExecutor)

  def get(routeMatchers: Regex, routeExecutor: RouteExecutor) = router.addApplicationRoute(HTTP.GET, routeMatchers, routeExecutor)

  def put(routeMatchers: Regex, routeExecutor: RouteExecutor) = router.addApplicationRoute(HTTP.PUT, routeMatchers, routeExecutor)

  def post(routeMatchers: Regex, routeExecutor: RouteExecutor) = router.addApplicationRoute(HTTP.POST, routeMatchers, routeExecutor)

  def delete(routeMatchers: Regex, routeExecutor: RouteExecutor) = router.addApplicationRoute(HTTP.DELETE, routeMatchers, routeExecutor)

}

