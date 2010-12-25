package org.bowlerframework

import model.RequestMapper

/**
 * Created by IntelliJ IDEA.
 * User: wfaler
 * Date: 25/12/2010
 * Time: 00:01
 * To change this template use File | Settings | File Templates.
 */

trait RequestMappingStrategy{
  def getRequestMapper(request: Request): RequestMapper
}