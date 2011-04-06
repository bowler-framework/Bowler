package org.bowlerframework.view.scalate.selectors

import util.matching.Regex


class HeaderSuffixSelector(suffix: String, headerSelectors: Map[String, Regex]) extends HeaderSelector[String](suffix, headerSelectors) with TemplateSuffixSelector