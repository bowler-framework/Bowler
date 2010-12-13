package org.bowlerframework.controller

import org.bowlerframework.RequestScope

/**
 * Created by IntelliJ IDEA.
 * User: wfaler
 * Date: 12/12/2010
 * Time: 23:42
 * To change this template use File | Settings | File Templates.
 */

object ParameterMapper{
  def map[T](scope: RequestScope, manifest: Manifest[T]): T = {
    return None.asInstanceOf[T]
  }
}