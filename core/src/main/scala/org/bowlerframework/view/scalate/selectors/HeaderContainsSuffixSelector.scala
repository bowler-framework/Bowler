package org.bowlerframework.view.scalate.selectors

/**
 * Created by IntelliJ IDEA.
 * User: wfaler
 * Date: 07/01/2011
 * Time: 19:28
 * To change this template use File | Settings | File Templates.
 */

class HeaderContainsSuffixSelector(suffix: String, headers: Map[String, String]) extends HeaderContainsSelector[String](suffix, headers) with TemplateSuffixSelector