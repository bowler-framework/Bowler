package org.bowlerframework.view.scalate.selectors

import org.bowlerframework.Request
import org.bowlerframework.view.scalate.Layout

/**
 * Created by IntelliJ IDEA.
 * User: wfaler
 * Date: 05/01/2011
 * Time: 21:58
 * To change this layout use File | Settings | File Templates.
 */

class HeaderContainsLayoutSelector(layout: Layout, headers: Map[String, String]) extends HeaderContainsSelector[Layout](layout, headers) with LayoutSelector