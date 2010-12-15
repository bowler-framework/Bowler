package org.bowlerframework.jvm

import org.bowlerframework.Session
import com.recursivity.commons.UUIDGenerator
import collection.mutable.{MutableList, HashMap}

/**
 * Created by IntelliJ IDEA.
 * User: wfaler
 * Date: 14/12/2010
 * Time: 00:14
 * To change this template use File | Settings | File Templates.
 */

class DummySession extends Session{
  private var creationTime = System.currentTimeMillis
  private var id = UUIDGenerator.generate
  private var attributeMap = new HashMap[String, Any]


  def getId = id

  def getAttributeNames: List[String] ={
    val list = new MutableList[String]
    attributeMap.keys.foreach(f => list += f)
    return list.toList
  }

  def getCreationTime = creationTime

  def invalidate = {
    id = null
    creationTime = java.lang.Long.MAX_VALUE
    attributeMap = null
  }

  def removeAttribute(name: String) = attributeMap.remove(name)

  def setAttribute[T](name: String, value: T) = attributeMap += name -> value

  def getAttribute[T](name: String): Option[T] = {
    try{
      return Some(attributeMap(name).asInstanceOf[T])
    }catch{
      case e: NoSuchElementException => return None
    }
  }
}