package org.bowlerframework.view.scalate.selectors

import util.matching.Regex

/**
 * Created by IntelliJ IDEA.
 * User: wfaler
 * Date: 07/01/2011
 * Time: 19:53
 * To change this template use File | Settings | File Templates.
 */

class UriSuffixSelector(suffix: String, uri: Regex) extends UriSelector[String](suffix, uri) with TemplateSuffixSelector