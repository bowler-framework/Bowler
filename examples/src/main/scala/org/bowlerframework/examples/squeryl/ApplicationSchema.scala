package org.bowlerframework.examples.squeryl

import org.squeryl.Schema

/**
 * Created by IntelliJ IDEA.
 * User: wfaler
 * Date: 20/02/2011
 * Time: 19:56
 * To change this template use File | Settings | File Templates.
 */

object ApplicationSchema extends Schema{
  val people = table[Person]("people")
}