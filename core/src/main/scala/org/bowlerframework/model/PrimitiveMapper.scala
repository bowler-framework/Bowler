package org.bowlerframework.model

/**
 * Created by IntelliJ IDEA.
 * User: wfaler
 * Date: 28/12/2010
 * Time: 17:02
 * To change this template use File | Settings | File Templates.
 */

object PrimitiveMapper{
  def getClassForPrimitive(m: String): Class[_] = {
    var fieldCls: Class[_] = null
    m match {
      case "Long" => fieldCls = classOf[Long]
      case "Int" => fieldCls = classOf[java.lang.Integer]
      case "Float" => fieldCls = classOf[java.lang.Float]
      case "Double" => fieldCls = classOf[java.lang.Double]
      case "Boolean" => fieldCls = classOf[Boolean]
      case "Short" => fieldCls = classOf[java.lang.Short]
      case _ => fieldCls = null
    }
    return fieldCls
  }
}