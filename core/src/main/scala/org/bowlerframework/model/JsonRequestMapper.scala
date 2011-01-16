package org.bowlerframework.model

import org.bowlerframework.{HTTP, Request}
import net.liftweb.json.JsonParser._
import com.recursivity.commons.bean.GenericsParser

/**
 * RequestMapper for JSON requests.
 */

class JsonRequestMapper extends RequestMapper {
  implicit val formats = net.liftweb.json.DefaultFormats

  def getValue[T](request: Request, nameHint: String = null)(implicit m: Manifest[T]): T = {
    var typeString = m.toString.replace("[", "<")
    typeString = typeString.replace("]", ">")
    val typeDef = GenericsParser.parseDefinition(typeString)

    if (request.getMethod == HTTP.GET || request.getMethod == HTTP.DELETE) {
      val mapper = new DefaultRequestMapper
      return mapper.getValue[T](request, nameHint)(m)
    } else{
      return getValue[T](request.getRequestBodyAsString, nameHint)
    }
  }

  private def getValue[T](body: String, nameHint: String)(implicit m: Manifest[T]): T = {
    val json = parse(body)
    if (nameHint == null) {
      return json.extract[T]
    } else {
      val namedJson = json \\ nameHint
      return namedJson.extract[T]
    }
  }

}