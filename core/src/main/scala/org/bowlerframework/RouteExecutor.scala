package org.bowlerframework

/**
 * Created by IntelliJ IDEA.
 * User: wfaler
 * Date: Dec 10, 2010
 * Time: 1:51:41 AM
 * To change this template use File | Settings | File Templates.
 */

trait RouteExecutor{
  def executeRoute(requestScope: RequestScope): Unit
}