package org.bowlerframework.view.scalate

import org.bowlerframework.Request



trait LayoutModel{
  def model(request: Request, viewModel: Map[String, Any], childView: String): Map[String, Any]
}