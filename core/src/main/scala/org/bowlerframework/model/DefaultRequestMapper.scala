package org.bowlerframework.model

import com.recursivity.commons.bean.{BeanUtils, GenericTypeDefinition, TransformerRegistry}
import org.apache.commons.fileupload.FileItem
import util.DynamicVariable
import collection.TraversableLike
import collection.mutable.{MutableList, HashMap}
import org.bowlerframework.{Session, PUT, POST, Request}

/**
 * Maps a single value from a request, for instance a bean from a request.
 */

class DefaultRequestMapper extends RequestMapper {

  private val _requestToMap = new DynamicVariable[Request](null)

  def httpRequest = _requestToMap value

  def getValueWithTypeDefinition(typeDefinition: GenericTypeDefinition, request: Request, nameHint: String): Any = {
    val map = new HashMap[String, Any]
    request.getParameterMap.foreach(f => map.put(f._1, f._2))
    _requestToMap.withValue(request) {
      return getValue[Any](map, nameHint, typeDefinition)
    }
  }

  def getValue[T](request: Request, nameHint: String = null)(implicit m: Manifest[T]): T = {
    val map = new HashMap[String, Any]
    request.getParameterMap.foreach(f => map.put(f._1, f._2))

    _requestToMap.withValue(request) {
      return getValue[T](map, nameHint, m)
    }
  }


  private def getValue[T](request: HashMap[String, Any], nameHint: String, m: Manifest[T]): T = {
    var typeString = m.toString.replace("[", "<")
    typeString = typeString.replace("]", ">")
    val typeDef = GenericTypeDefinition(typeString)
    return getValue[T](request, nameHint, typeDef)
  }


  private def getValue[T](request: HashMap[String, Any], nameHint: String, typeDef: GenericTypeDefinition): T = {
    val primitive = getValueForPrimitive[T](request, nameHint, typeDef.clazz)
    if (primitive != None)
      return primitive

    val cls = typeDef.definedClass

    if (classOf[FileItem].isAssignableFrom(cls) || (classOf[Seq[_]].isAssignableFrom(cls) && typeDef.genericTypes != None && typeDef.genericTypes.get(0).definedClass.isAssignableFrom(classOf[FileItem])))
      return getFileValues[T](request, nameHint, typeDef)
    if(classOf[Request].isAssignableFrom(cls))
      return httpRequest.asInstanceOf[T]
    if(classOf[Session].isAssignableFrom(cls))
      return httpRequest.getSession.asInstanceOf[T]

    if (typeDef.genericTypes == None) {
      // deal with non-generics, non-files, non-primitives
      val alias = AliasRegistry(typeDef)
      var hintOfName: String = nameHint
      var dealiasedRequest = getDealiasedRequest(alias, request)

      if (dealiasedRequest.keys.size == 0) dealiasedRequest = request
      else {
        if (hintOfName != null && hintOfName.startsWith(alias + "."))
          hintOfName.substring(alias.length + 1)
      }

      val response = {
        try{
          getValueForTransformer[T](dealiasedRequest, hintOfName, cls)
        }catch{
          case e: NoSuchElementException => {
            if(hintOfName != null)
              getValue[T](request, null, typeDef)
            else throw e
          }
        }
      }
      if (response == null && !TransformerRegistry(cls).equals(None) && (httpRequest.getMethod.equals(POST) || httpRequest.getMethod.equals(PUT))) {
        return BeanUtils.instantiate[T](cls, dealiasedRequest.toMap)
      } else if (response == null && (!httpRequest.getMethod.equals(POST) || !httpRequest.getMethod.equals(PUT))) {
        return None.asInstanceOf[T]
      } else if (response != None || !TransformerRegistry(cls).equals(None)) {
        if (httpRequest.getMethod.equals(POST) || httpRequest.getMethod.equals(PUT))
          return BeanUtils.setProperties[T](response, dealiasedRequest.toMap)
        else
          return response
      } else if (httpRequest.getMethod.equals(POST) || httpRequest.getMethod.equals(PUT) || classOf[Transient].isAssignableFrom(cls)) {
        return BeanUtils.instantiate[T](cls, dealiasedRequest.toMap)
      } else {
        return None.asInstanceOf[T]
      }
    } else {
      // deal with Generics
      return getGenerifiedValue[T](request, nameHint, typeDef)
    }
    return None.asInstanceOf[T]
  }

  private def getDealiasedRequest(alias: String, request: HashMap[String, Any]): HashMap[String, Any] = {
    var dealiasedRequest = new HashMap[String, Any]
    request.iterator.foreach(f => {
      if (f._1.startsWith(alias + ".")) {
        val key = f._1.substring(alias.length + 1)
        dealiasedRequest.put(key, f._2)
      }
    })
    //if (dealiasedRequest.keys.size == 0) dealiasedRequest = request
    return dealiasedRequest
  }


  private def getGenerifiedValue[T](request: HashMap[String, Any], nameHint: String, typeDef: GenericTypeDefinition): T = {
    val newTypeDef = typeDef.genericTypes.get(0)
    val clazz = typeDef.definedClass
    if (clazz.equals(classOf[Option[_]])) {
      val value = getValue[Any](request, nameHint, newTypeDef)
      if (value != None) {
        return Some(value).asInstanceOf[T]
      }
    } else if (classOf[TraversableLike[_ <: Any, _ <: Any]].isAssignableFrom(clazz)) {
      val values = valueList(typeDef, newTypeDef, request, nameHint)
      if (values.isEmpty)
        return None.asInstanceOf[T]
      else
        return BeanUtils.resolveTraversableOrArray(clazz, values).asInstanceOf[T]
    } else if (classOf[java.util.Collection[_ <: Any]].isAssignableFrom(clazz)) {
      val values = valueList(typeDef, newTypeDef, request, nameHint)
      if (values.isEmpty)
        return None.asInstanceOf[T]
      else
        return BeanUtils.resolveJavaCollectionType(clazz, values).asInstanceOf[T]
    }
    return None.asInstanceOf[T]
  }


  private def valueList(parent: GenericTypeDefinition, typeDef: GenericTypeDefinition, request: HashMap[String, Any], nameHint: String): MutableList[Any] = {
    val values = new MutableList[Any]
    var list: List[Any] = null
    if (nameHint != null)
      list = request(nameHint).asInstanceOf[List[_]]
    else {
      val alias = AliasRegistry(parent)

      request.iterator.foreach(f => {
        if (list == null && f._2.isInstanceOf[AnyRef] && classOf[List[_ <: Any]].isAssignableFrom(f._2.asInstanceOf[AnyRef].getClass) && f._1.startsWith(alias))
          list = f._2.asInstanceOf[List[_]]
      })
      if (list == null) {
        request.iterator.foreach(f => {
          if (list == null && f._2.isInstanceOf[AnyRef] && classOf[List[_ <: Any]].isAssignableFrom(f._2.asInstanceOf[AnyRef].getClass))
            list = f._2.asInstanceOf[List[_]]
        })
      }
    }
    list.foreach(entry => {
      val map = new HashMap[String, Any]
      map.put("value", entry)
      val value = getValue[Any](map, "value", typeDef)
      if (value != None)
        values += value
    })
    return values
  }


  private def getFileValues[T](request: HashMap[String, Any], nameHint: String, typeDef: GenericTypeDefinition): T = {
    val cls = typeDef.definedClass
    if (classOf[FileItem].isAssignableFrom(cls)) {
      if (nameHint != null)
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

    } else if (classOf[Seq[_]].isAssignableFrom(cls) && typeDef.genericTypes != None && typeDef.genericTypes.get(0).definedClass.isAssignableFrom(classOf[FileItem])) {
      if (nameHint != null)
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
    } else return None.asInstanceOf[T]

  }


  private def getValueForTransformer[T](request: HashMap[String, Any], nameHint: String, cls: Class[_]): T = {
    if (nameHint != null) {
      val response = TransformerRegistry(cls).getOrElse(return None.asInstanceOf[T]).toValue(request(nameHint).toString).getOrElse(null).asInstanceOf[T]
      if (response != null && response != None) {
        request.remove(nameHint)
        return response
      } else
        return None.asInstanceOf[T]
    }
    else {
      var response: T = None.asInstanceOf[T]
      val transformer = TransformerRegistry(cls).getOrElse(return None.asInstanceOf[T])
      request.iterator.find(f => {
        try {
          response = transformer.toValue(f._2.toString).getOrElse(null).asInstanceOf[T]
          if (response != null && response != None) {
            request.remove(f._1)
            return response
          }
          false
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
    val cls = PrimitiveMapper.getClassForPrimitive(m)
    if (cls != null) {
      var result = getValueForTransformer[T](request, nameHint, cls)
      if (result == None && nameHint != null) {
        return getValueForPrimitive[T](request, null, m)
      } else if (result == None)
        throw new RequestMapperException("Cannot map parameter of type " + m)
      else return result
    } else return None.asInstanceOf[T]
  }


}