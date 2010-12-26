package org.bowlerframework.model

import collection.mutable.HashMap
import com.recursivity.commons.bean.{BeanUtils, GenericsParser, GenericTypeDefinition, TransformerRegistry}
import org.apache.commons.fileupload.FileItem
import util.DynamicVariable
import org.bowlerframework.{HTTP, Request}

/**
 * Created by IntelliJ IDEA.
 * User: wfaler
 * Date: 21/12/2010
 * Time: 21:57
 * To change this template use File | Settings | File Templates.
 */

class DefaultRequestMapper extends RequestMapper{

  private val _requestToMap = new DynamicVariable[Request](null)

  def httpRequest = _requestToMap value

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
      val alias = AliasRegistry.getAlias(typeDef)
      var dealiasedRequest = new HashMap[String, Any]
      var hintOfName: String = nameHint
      request.iterator.foreach(f => {
        if(f._1.startsWith(alias + ".")){
          val key = f._1.substring(alias.length + 1)
          dealiasedRequest.put(key, f._2)
        }
      })

      if(dealiasedRequest.keys.size == 0) dealiasedRequest = request
      else{
        if(hintOfName != null && hintOfName.startsWith(alias + "."))
          hintOfName.substring(alias.length + 1)
      }

      val response = getValueForTransformer[T](dealiasedRequest, hintOfName, cls)
      if(response == null && !TransformerRegistry.resolveTransformer(cls).equals(None) && (httpRequest.getMethod.equals(HTTP.POST) || httpRequest.getMethod.equals(HTTP.PUT))){
        return BeanUtils.instantiate[T](cls, dealiasedRequest.toMap)
      }else if(response == null && (!httpRequest.getMethod.equals(HTTP.POST) || !httpRequest.getMethod.equals(HTTP.PUT))){
        return None.asInstanceOf[T]
      }else if(response != None || !TransformerRegistry.resolveTransformer(cls).equals(None)){
        if(httpRequest.getMethod.equals(HTTP.POST) || httpRequest.getMethod.equals(HTTP.PUT))
          return BeanUtils.setProperties[T](response, dealiasedRequest.toMap)
        else
          return response
      }else if(httpRequest.getMethod.equals(HTTP.POST) || httpRequest.getMethod.equals(HTTP.PUT) || classOf[Transient].isAssignableFrom(cls)){
        return BeanUtils.instantiate[T](cls, dealiasedRequest.toMap)
      }else{
        return None.asInstanceOf[T]
      }
    }else{   // deal with Generics
      return getGenerifiedValue[T](request, nameHint, typeDef)
    }
    return None.asInstanceOf[T]
  }


  private def getGenerifiedValue[T](request: HashMap[String, Any], nameHint: String, typeDef: GenericTypeDefinition): T = {
    val clazz = Class.forName(typeDef.clazz)
    if(clazz.equals(classOf[Option[_]])){
      val newTypeDef = typeDef.genericTypes.get(0)
      val value = getValue[Any](request, nameHint, newTypeDef)
      if(value != None){
        return Some(value).asInstanceOf[T]
      }
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
      if(response != null && response != None){
        request.remove(nameHint)
        return response
      }else
        return None.asInstanceOf[T]
    }
    else {
      var response: T = None.asInstanceOf[T]
      val transformer = TransformerRegistry.resolveTransformer(cls).getOrElse(return None.asInstanceOf[T])
      request.iterator.find(f => {
        try {
          response = transformer.toValue(f._2.toString).asInstanceOf[T]
          if(response != null && response != None){
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