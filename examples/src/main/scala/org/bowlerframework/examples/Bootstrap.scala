package org.bowlerframework.examples

import org.bowlerframework.view.scalate._
import org.bowlerframework.view.scalate.selectors._

import com.recursivity.commons.bean.{StringValueTransformer, TransformerRegistry}

class Bootstrap{
	TemplateRegistry.appendTemplateSelectors(List(new DefaultLayoutSelector(Layout("default"))))
  TransformerRegistry.registerTransformer(classOf[Widget], classOf[WidgetTransformer])
	val controller = new WidgetController
}

class WidgetTransformer extends StringValueTransformer{
  def toValue(from: String): AnyRef = {
    val widget = Widgets.find(Integer.parseInt(from))
    if(widget == None)
      return null
    else
      return widget.get
  }
}

