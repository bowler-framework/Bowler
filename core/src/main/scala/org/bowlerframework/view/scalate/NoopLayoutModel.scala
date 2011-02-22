package org.bowlerframework.view.scalate

import org.bowlerframework.Request

class NoopLayoutModel(viewName: String = "doLayout") extends LayoutModel{
  def model(request: Request, viewModel: Map[String, Any], childView: String) = Map[String, Any](viewName -> childView)
}