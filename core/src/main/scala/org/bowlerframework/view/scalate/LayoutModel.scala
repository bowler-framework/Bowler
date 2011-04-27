package org.bowlerframework.view.scalate

import org.bowlerframework.Request


trait LayoutModel {
  def model(request: Request, viewModel: Map[String, Any], viewIdAndValue: Tuple2[String, String]): Map[String, Any]
}