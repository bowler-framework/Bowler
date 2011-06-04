package org.bowlerframework.view

import org.bowlerframework.{MappedPath, GET, HttpMethod}

/**
 * Created by IntelliJ IDEA.
 * User: wfaler
 * Date: 17/03/2011
 * Time: 23:33
 * To change this template use File | Settings | File Templates.
 */

case class ViewPath(method: HttpMethod, path: MappedPath)

object ViewPath{
  def apply(path: String): ViewPath = ViewPath(GET, MappedPath(path))
  def apply(method: HttpMethod, path: String): ViewPath = ViewPath(method, MappedPath(path))
  implicit def stringToViewPath(path: String): ViewPath = ViewPath(GET, MappedPath(path))
}