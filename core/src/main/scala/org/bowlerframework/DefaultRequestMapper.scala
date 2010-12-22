package org.bowlerframework

import collection.mutable.HashMap
import com.recursivity.commons.bean.{BeanUtils, GenericsParser, GenericTypeDefinition, TransformerRegistry}
import org.apache.commons.fileupload.FileItem

/**
 * Created by IntelliJ IDEA.
 * User: wfaler
 * Date: 21/12/2010
 * Time: 21:57
 * To change this template use File | Settings | File Templates.
 */

class DefaultRequestMapper extends RequestMapper{

  def getValue[T](request: Request, nameHint: String = null)(implicit m: Manifest[T]): T = {
    val map = new HashMap[String, Any]
    request.getParameterMap.foreach(f => map.put(f._1, f._2))

    return getValue[T](map, nameHint, m)
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

    if(classOf[FileItem].isAssignableFrom(cls) || (classOf[Seq[_]].isAssignableFrom(cls) && typeDef.genericTypes != None && Class.forName(typeDef.genericTypes.get(0).clazz).isAssignableFrom(classOf[FileItem])))
      return getFileValues[T](request, nameHint, typeDef)

    if (typeDef.genericTypes == None) {   // deal with non-generics, non-files, non-primitives
      val response = getValueForTransformer[T](request, nameHint, cls)
      if(response != None || !TransformerRegistry.resolveTransformer(cls).equals(None))
        return response
      return BeanUtils.instantiate[T](cls, request.toMap)
    } else { // deal with generified type
      // deal with generified type
    }
    return None.asInstanceOf[T]
  }


  private def getFileValues[T](request: HashMap[String, Any], nameHint: String, typeDef: GenericTypeDefinition): T = {
    val cls = Class.forName(typeDef.clazz)
    if(classOf[FileItem].isAssignableFrom(cls)){
      if(nameHint != null)
        return request(nameHint).asInstanceOf[T]
      var response: T = None.asInstanceOf[T]
      request.iterator.find(f => {
        try {
          response = f._2.asInstanceOf[T]
          request.remove(f._1)
          return response
        } catch {
          case e: Exception => {
            false
          }
        }
      })
      return response

    }else if(classOf[Seq[_]].isAssignableFrom(cls) && typeDef.genericTypes != None && Class.forName(typeDef.genericTypes.get(0).clazz).isAssignableFrom(classOf[FileItem])){
      if(nameHint != null)
        return request(nameHint).asInstanceOf[T]
      var response: T = None.asInstanceOf[T]
      request.iterator.find(f => {
        try {
          response = f._2.asInstanceOf[T]
          request.remove(f._1)
          return response
        } catch {
          case e: Exception => {
            false
          }
        }
      })
      return response
    }else return None.asInstanceOf[T]

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