package org.bowlerframework.view.json

import net.liftweb.json.{MappingException, TypeInfo, Formats, Serializer}
import net.liftweb.json.JsonAST._

/**
 * Created by IntelliJ IDEA.
 * User: wfaler
 * Date: 05/05/2011
 * Time: 22:18
 * To change this template use File | Settings | File Templates.
 */

class BigDecimalSerializer extends Serializer[BigDecimal] {
  private val Class = classOf[BigDecimal]

  def deserialize(implicit format: Formats): PartialFunction[(TypeInfo, JValue), BigDecimal] = {
    case (TypeInfo(Class, _), json) => json match {
      case JInt(iv) => BigDecimal(iv)
      case JDouble(dv) => BigDecimal(dv)
      case JString(s) => new BigDecimal(new java.math.BigDecimal(s))
      case x => throw new MappingException("Can't convert " + Class + " to BigDecimal" + json)
    }

  }

  def serialize(implicit format: Formats): PartialFunction[Any, JValue] = {
    case x: BigDecimal => JString(x.toString)
  }
}