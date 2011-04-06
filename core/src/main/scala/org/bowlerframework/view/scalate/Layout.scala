package org.bowlerframework.view.scalate


case class Layout(name: String, parentLayout: Option[Layout] = None, layoutModel: LayoutModel = new NoopLayoutModel)