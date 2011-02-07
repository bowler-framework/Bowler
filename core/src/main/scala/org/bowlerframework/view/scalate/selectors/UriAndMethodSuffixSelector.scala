package org.bowlerframework.view.scalate.selectors

import util.matching.Regex
import org.bowlerframework.{HttpMethod, HTTP}

/**
 * Created by IntelliJ IDEA.
 * User: wfaler
 * Date: 07/01/2011
 * Time: 19:54
 * To change this template use File | Settings | File Templates.
 */

class UriAndMethodSuffixSelector(suffix: String, method: HttpMethod, uri: Regex) extends UriAndMethodSelector[String](suffix, method, uri) with TemplateSuffixSelector