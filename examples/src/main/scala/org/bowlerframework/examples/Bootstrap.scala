package org.bowlerframework.examples

import org.bowlerframework.view.scalate._
import org.bowlerframework.view.scalate.selectors._

import com.recursivity.commons.bean.{StringValueTransformer, TransformerRegistry}
import util.matching.Regex

/**
 * This class acts as the starting point and bootstrap point for our application
 */
class Bootstrap{
  // parent layout, that uses a LayoutModel to enrich the layout based on request if needed.
  val parentLayout = Layout("default", None, new ParentLayoutModel)

  // this is a childLayout for parentLayout, and has the parent set on it, as shown.
  val composableLayout = Layout("child", Some(parentLayout))


  //You can define which layout to use based on chaining LayoutSelectors, for instance based on URL,
  // User-Agent or other factors.
	TemplateRegistry.appendLayoutSelectors(List(
    new UriLayoutSelector(composableLayout, new Regex("^.*/composable/.*$")),
    new DefaultLayoutSelector(parentLayout)))

  // Example of overriding view template URI's, in this case we make the path of /composable/ point
  // to the same template as for /widgets/
  TemplateRegistry.overridePath("/composable/", "/views/GET/widgets/index")

  // Register the WidgetTransformer so that we can look up Widgets for pages by ID
  TransformerRegistry.registerTransformer(classOf[Widget], classOf[WidgetTransformer])

  // I think we're ready to start and instantiate our Controller.
	val controller = new WidgetController
}

/**
 * Transforms from single request parameters to an object, for instance in this case from a Widget ID
 * to an Actual Widget, or returns None if a transformation cannot be done.<br/>
 * Must be registered with the TransformerRegistry, as done in the bootstrap above.
 */
class WidgetTransformer extends StringValueTransformer[Widget]{
  def toValue(from: String): Option[Widget] = {
    val widget = Widgets.find(Integer.parseInt(from))
    if(widget == None)
      return None
    else
      return widget
  }
}

