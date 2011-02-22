package org.bowlerframework.view.scalate.selectors


class HeaderContainsSuffixSelector(suffix: String, headers: Map[String, String]) extends HeaderContainsSelector[String](suffix, headers) with TemplateSuffixSelector