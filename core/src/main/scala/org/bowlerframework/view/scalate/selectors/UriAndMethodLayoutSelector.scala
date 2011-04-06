package org.bowlerframework.view.scalate.selectors

import util.matching.Regex
import org.bowlerframework.view.scalate.Layout
import org.bowlerframework.HttpMethod

class UriAndMethodLayoutSelector(layout: Layout, method: HttpMethod, uri: Regex) extends UriAndMethodSelector[Layout](layout, method, uri) with LayoutSelector