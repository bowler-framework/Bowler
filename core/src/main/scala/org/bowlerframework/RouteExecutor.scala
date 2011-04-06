package org.bowlerframework


trait RouteExecutor {
  def executeRoute(requestScope: RequestScope): Unit
}