package org.bowlerframework.view.scalate.selectors

import util.matching.Regex


class UriSuffixSelector(suffix: String, uri: Regex) extends UriSelector[String](suffix, uri) with TemplateSuffixSelector