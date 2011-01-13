package org.bowlerframework.view.scalate

import org.bowlerframework.Request
import collection.mutable.HashMap

/**
 * Created by IntelliJ IDEA.
 * User: wfaler
 * Date: 08/01/2011
 * Time: 03:04
 * To change this template use File | Settings | File Templates.
 */

trait LayoutModel{
  def model(request: Request, viewModel: Option[Map[String, Any]]): HashMap[String, Any]
}