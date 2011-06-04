package org.bowlerframework

/**
 * A Case class "bean" representation of a path mapped in Scalatra format, such as /widgets/:id
 */

case class MappedPath(path: String, isRegex: Boolean = false)
