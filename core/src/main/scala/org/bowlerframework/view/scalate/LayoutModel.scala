package org.bowlerframework.view.scalate

import org.bowlerframework.Request


trait LayoutModel {
  def model(request: Request, viewIdAndValue: Tuple2[String, String]): Map[String, Any]
}