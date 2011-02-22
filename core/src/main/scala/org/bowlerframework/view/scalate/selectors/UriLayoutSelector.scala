package org.bowlerframework.view.scalate.selectors

import util.matching.Regex
import org.bowlerframework.view.scalate.Layout


class UriLayoutSelector(layout: Layout, uri: Regex) extends UriSelector[Layout](layout, uri) with LayoutSelector