package org.bowlerframework

/**
 * Created by IntelliJ IDEA.
 * User: wfaler
 * Date: 16/12/2010
 * Time: 22:28
 * To change this template use File | Settings | File Templates.
 */

object HTTP extends Enumeration{
  type Method = Value

  val GET, POST, PUT, DELETE = Value

}