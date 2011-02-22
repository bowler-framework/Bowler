package org.bowlerframework.view.scalate.selectors

import org.bowlerframework.view.scalate.Layout

class HeaderContainsLayoutSelector(layout: Layout, headers: Map[String, String]) extends HeaderContainsSelector[Layout](layout, headers) with LayoutSelector