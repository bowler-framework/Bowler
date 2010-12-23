package org.bowlerframework.model

import collection.mutable.HashMap

/**
 * Created by IntelliJ IDEA.
 * User: wfaler
 * Date: 22/12/2010
 * Time: 23:11
 * To change this template use File | Settings | File Templates.
 */

object AliasRegistry{
  val map = new HashMap[Class[_], String]
  def getAlias(cls: Class[_]): String = {
    try{
      return map(cls)
    }catch{
      case e: NoSuchElementException => {
        return cls.getSimpleName
      }
    }
  }

  def registerAlias(cls: Class[_], alias: String) = map.put(cls, alias)
}