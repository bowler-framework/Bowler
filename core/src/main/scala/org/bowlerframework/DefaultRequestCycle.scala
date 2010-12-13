package org.bowlerframework

/**
 * Created by IntelliJ IDEA.
 * User: wfaler
 * Date: 12/12/2010
 * Time: 23:18
 * To change this template use File | Settings | File Templates.
 */

class DefaultRequestCycle extends RequestCycle{
  def onAfter = {}

  def onBefore = {}

  def onError(e: Exception) = null
}