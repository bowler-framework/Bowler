package org.bowlerframework.view.scalate.selectors

import org.bowlerframework.Request
import org.bowlerframework.view.scalate.Layout

/**
 * LayoutSelector that simply returns the layout provided.<br/>
 * NOTE! This should be added/put last of all selectors, or other selectors will never be invoked!
 */

class DefaultLayoutSelector(layout: Layout) extends LayoutSelector{
  def layout(request: Request) = Some(layout)
}