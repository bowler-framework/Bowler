package org.bowlerframework

/**
 * Created by IntelliJ IDEA.
 * User: wfaler
 * Date: 12/12/2010
 * Time: 23:16
 * To change this template use File | Settings | File Templates.
 */

trait RequestCycle{
  var scope: RequestScope = null

  def init(scope: RequestScope) = (this.scope = scope)

  def onBefore: Unit
  def onError(e: Exception): Any
  def onAfter: Unit
}