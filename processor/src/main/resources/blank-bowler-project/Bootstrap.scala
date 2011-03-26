package bowlerquickstart

import org.bowlerframework.view.scalate._
import org.bowlerframework.view.scalate.selectors._

/**
 * This class acts as the starting point and bootstrap point for our application
 */
class Bootstrap{
  // parent layout
  val parentLayout = Layout("default")


  //You can define which layout to use based on chaining LayoutSelectors, for instance based on URL,
  // User-Agent or other factors.
  TemplateRegistry.appendLayoutSelectors(List(new DefaultLayoutSelector(parentLayout)))


  // I think we're ready to start and instantiate our Controller.
  val controller = new MyController
	
	
  // allow template reload during development - remove these lines in production for better performance
  org.bowlerframework.view.scalate.RenderEngine.getEngine.allowCaching = false
  org.bowlerframework.view.scalate.RenderEngine.getEngine.allowReload = true
}