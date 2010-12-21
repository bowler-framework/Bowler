package org.bowlerframework.controller

import org.bowlerframework.{Request}
import collection.mutable.HashMap
import com.recursivity.commons.bean.{BeanUtils, GenericTypeDefinition, GenericsParser, TransformerRegistry}

/**
 * Created by IntelliJ IDEA.
 * User: wfaler
 * Date: 12/12/2010
 * Time: 23:42
 * To change this template use File | Settings | File Templates.
 */

trait RequestMapper {
  def mapRequest[T](request: Request, nameHint: String = null)(func: T => Any)(implicit m: Manifest[T]): Any = {
    val map = new HashMap[String, Any]
    // check for JSON OR XML - use BowlerConfigurator to check alternative mappers?
    request.getParameterMap.foreach(f => map.put(f._1, f._2))

    val param = getValue[T](map, nameHint, m)
    if (param != None)
      func(param)
    else {
      try {
        func(None.asInstanceOf[T])
      } catch {
        case e: ClassCastException => throw new RequestMapperException("Could not map parameter to a value! If a " +
          "parameter is not mandatory, you should consider using Option[" + m.toString + "]!")
      }
    }

  }

  private def getValue[T](request: HashMap[String, Any], nameHint: String, m: Manifest[T]): T = {
    var typeString = m.toString.replace("[", "<")
    typeString = typeString.replace("]", ">")
    val typeDef = GenericsParser.parseDefinition(typeString)
    return getValue[T](request, nameHint, typeDef)
  }

  private def getValue[T](request: HashMap[String, Any], nameHint: String, typeDef: GenericTypeDefinition): T = {
    val primitive = getValueForPrimitive[T](request, nameHint, typeDef.clazz)
    if (primitive != None)
      return primitive
    val cls = Class.forName(typeDef.clazz)
    if (typeDef.genericTypes == None) {
      val response = getValueForTransformer[T](request, nameHint, cls)
      if(response != None)
        return response
      return BeanUtils.instantiate[T](cls, request.toMap)
    } else {
      // deal with generified type
    }
    return None.asInstanceOf[T]
  }


  private def getValueForTransformer[T](request: HashMap[String, Any], nameHint: String, cls: Class[_]): T = {
    if (nameHint != null) {
      val response = TransformerRegistry.resolveTransformer(cls).getOrElse(return None.asInstanceOf[T]).toValue(request(nameHint).toString).asInstanceOf[T]
      request.remove(nameHint)
      return response
    }
    else {
      var response: T = None.asInstanceOf[T]
      val transformer = TransformerRegistry.resolveTransformer(cls).getOrElse(return None.asInstanceOf[T])
      request.iterator.find(f => {
        try {
          response = transformer.toValue(f._2.toString).asInstanceOf[T]
          request.remove(f._1)
          return response
        } catch {
          case e: Exception => {
            false
          }
        }
      })
      return response
    }
  }


  private def getValueForPrimitive[T](request: HashMap[String, Any], nameHint: String, m: String): T = {
    val cls = getClassForPrimitive(m)
    if (cls != null) {
      var result = getValueForTransformer[T](request, nameHint, cls)
      if(result == None && nameHint != null){
        return getValueForPrimitive[T](request, null, m)
      }else if(result == None)
        throw new RequestMapperException("Cannot map parameter of type " + m)
      else return result
    } else return None.asInstanceOf[T]
  }

  private def getClassForPrimitive(m: String): Class[_] = {
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