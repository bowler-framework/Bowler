package org.bowlerframework.view.scalate.selectors

import util.matching.Regex

/**
 * Created by IntelliJ IDEA.
 * User: wfaler
 * Date: 07/01/2011
 * Time: 19:46
 * To change this template use File | Settings | File Templates.
 */

class HeaderSuffixSelector(suffix: String, headerSelectors: Map[String, Regex]) extends HeaderSelector[String](suffix, headerSelectors) with TemplateSuffixSelector