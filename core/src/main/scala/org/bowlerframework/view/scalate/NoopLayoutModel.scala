package org.bowlerframework.view.scalate

import org.bowlerframework.Request

/**
 * Created by IntelliJ IDEA.
 * User: wfaler
 * Date: 13/01/2011
 * Time: 00:27
 * To change this template use File | Settings | File Templates.
 */

class NoopLayoutModel(viewName: String = "doLayout") extends LayoutModel{
  def model(request: Request, viewModel: Map[String, Any], childView: String) = Map[String, Any](viewName -> childView)
}