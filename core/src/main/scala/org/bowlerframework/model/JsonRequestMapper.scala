package org.bowlerframework.model

import net.liftweb.json.JsonParser._
import org.bowlerframework.{GET, DELETE, Request}
import com.recursivity.commons.bean.GenericTypeDefinition
import net.liftweb.json.{MappingException, Extraction, TypeInfo}

/**
 * RequestMapper for JSON requests.
 */

class JsonRequestMapper extends RequestMapper {
  implicit val formats = net.liftweb.json.DefaultFormats

  def getValue[T](request: Request, nameHint: String = null)(implicit m: Manifest[T]): T = {
    if (request.getMethod == GET || request.getMethod == DELETE) {
      val mapper = new DefaultRequestMapper
      return mapper.getValue[T](request, nameHint)(m)
    } else {
      var typeString = m.toString.replace("[", "<")
      typeString = typeString.replace("]", ">")
      val typeDef = GenericTypeDefinition(typeString)
      return getValueWithTypeDefinition(typeDef, request, nameHint).asInstanceOf[T]
    }
  }

  def getValueWithTypeDefinition(typeDefinition: GenericTypeDefinition, request: Request, nameHint: String): Any = {
    if (request.getMethod == GET || request.getMethod == DELETE) {
      val mapper = new DefaultRequestMapper
      return mapper.getValueWithTypeDefinition(typeDefinition, request, nameHint)
    } else {
      val jsonString = request.getRequestBodyAsString
      if (classOf[Option[_]].isAssignableFrom(typeDefinition.definedClass)) {
        val typeInfo = new TypeInfo(typeDefinition.genericTypes.get(0).definedClass, None)
        try{
          return Some(value(jsonString, typeInfo, nameHint))
        }catch{
          case e: MappingException => return None
        }
      } else {
        val typeInfo = new TypeInfo(typeDefinition.definedClass, None)
        return value(jsonString, typeInfo, nameHint)
      }
    }
  }

  private def value(jsonString: String, typeInfo: TypeInfo, nameHint: String): Any = {
    val json = parse(jsonString)
    if (nameHint == null) {
      return Extraction.extract(json, typeInfo)
    } else {
      val namedJson = json \\ nameHint
      return Extraction.extract(namedJson, typeInfo)
    }
  }


}