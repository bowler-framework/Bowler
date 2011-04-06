package org.bowlerframework.view.scalate.selectors

import util.matching.Regex
import org.bowlerframework.HttpMethod


class UriAndMethodSuffixSelector(suffix: String, method: HttpMethod, uri: Regex) extends UriAndMethodSelector[String](suffix, method, uri) with TemplateSuffixSelector