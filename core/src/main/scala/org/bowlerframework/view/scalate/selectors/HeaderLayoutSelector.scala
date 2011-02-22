package org.bowlerframework.view.scalate.selectors

import org.bowlerframework.view.scalate.Layout
import util.matching.Regex



class HeaderLayoutSelector(layout: Layout, headerSelectors: Map[String, Regex]) extends HeaderSelector[Layout](layout, headerSelectors) with LayoutSelector