package org.bowlerframework.controller

import org.bowlerframework.{Request, RequestScope}
import collection.mutable.HashMap
import com.recursivity.commons.bean.{GenericsParser, TransformerRegistry}


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
    else{
      try{
        func(None.asInstanceOf[T])
      }catch{
        case e: ClassCastException => throw new RequestMapperException("Could not map parameter to a value! If a " +
          "parameter cannot be mapped, you should consider using Option[T] for any values that are not mandatory!")
      }
    }

  }

  // TODO: change this to use the typeDef instead of manifest and extract manifest check to higher level
  private def getValue[T](request: HashMap[String, Any], nameHint: String, m: Manifest[T]): T = {
    val primitive = getValueForPrimitive(request, nameHint, m)
    if (primitive != None)
      return primitive
    var typeString = m.toString.replace("[", "<")
    typeString = typeString.replace("]", ">")
    val typeDef = GenericsParser.parseDefinition(typeString)

    if(typeDef.genericTypes == None){

    }else{
      // deal with generified type
    }
    return None.asInstanceOf[T]

  }


  private def getValueForPrimitive[T](request: HashMap[String, Any], nameHint: String, m: Manifest[T]): T = {
    val cls = getClassForPrimitive(m)
    if (cls != null) {
      if (nameHint != null) {
        val response = TransformerRegistry.resolveTransformer(cls).getOrElse(return getValueForPrimitive[T](request, null, m)).toValue(request(nameHint).toString).asInstanceOf[T]
        request.remove(nameHint)
        return response
      }
      else {
        var response: T = None.asInstanceOf[T]
        val transformer = TransformerRegistry.resolveTransformer(cls).getOrElse(throw new RequestMapperException("Cannot map parameter of type " + cls.getName))
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
    }else return None.asInstanceOf[T]

  }

  private def getClassForPrimitive(m: Manifest[_]): Class[_] = {
    var fieldCls: Class[_] = null
    m.toString match {
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