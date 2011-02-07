package org.bowlerframework.view.scalate.selectors

import util.matching.Regex
import org.bowlerframework.view.scalate.Layout
import org.bowlerframework.{HttpMethod, Request, HTTP}

/**
 * Created by IntelliJ IDEA.
 * User: wfaler
 * Date: 05/01/2011
 * Time: 22:38
 * To change this layout use File | Settings | File Templates.
 */

class UriAndMethodLayoutSelector(layout: Layout, method: HttpMethod, uri: Regex) extends UriAndMethodSelector[Layout](layout, method, uri) with LayoutSelector