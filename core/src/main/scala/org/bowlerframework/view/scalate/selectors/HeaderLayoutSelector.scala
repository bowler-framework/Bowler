package org.bowlerframework.view.scalate.selectors

import org.bowlerframework.view.scalate.Layout
import org.bowlerframework.Request
import util.matching.Regex

/**
 * Created by IntelliJ IDEA.
 * User: wfaler
 * Date: 05/01/2011
 * Time: 21:58
 * To change this layout use File | Settings | File Templates.
 */

class HeaderLayoutSelector(layout: Layout, headerSelectors: Map[String, Regex]) extends HeaderSelector[Layout](layout, headerSelectors) with LayoutSelector