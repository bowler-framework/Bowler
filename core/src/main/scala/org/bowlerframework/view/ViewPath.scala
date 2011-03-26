package org.bowlerframework.view

import org.bowlerframework.{HttpMethod, MappedPath}

/**
 * Created by IntelliJ IDEA.
 * User: wfaler
 * Date: 17/03/2011
 * Time: 23:33
 * To change this template use File | Settings | File Templates.
 */

case class ViewPath(method: HttpMethod, path: MappedPath)