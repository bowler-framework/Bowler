package org.bowlerframework.model

import collection.mutable.HashMap
import com.recursivity.commons.bean.GenericTypeDefinition

/**
 * Created by IntelliJ IDEA.
 * User: wfaler
 * Date: 22/12/2010
 * Time: 23:11
 * To change this template use File | Settings | File Templates.
 */

object AliasRegistry{
  val map = new HashMap[GenericTypeDefinition, String]

  // TODO: Maybe use TypeDef instead?
  def getAlias(cls: GenericTypeDefinition): String = {
    try{
      return map(cls)
    }catch{
      case e: NoSuchElementException => {
        return cls.toSimpleString
      }
    }
  }

  def registerAlias(cls: GenericTypeDefinition, alias: String) = map.put(cls, alias)
}