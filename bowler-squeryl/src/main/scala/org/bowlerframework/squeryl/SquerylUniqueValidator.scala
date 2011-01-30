package org.bowlerframework.squeryl

import com.recursivity.commons.validator.Validator
import org.squeryl.KeyedEntity

/**
 * Created by IntelliJ IDEA.
 * User: wfaler
 * Date: 30/01/2011
 * Time: 15:30
 * To change this template use File | Settings | File Templates.
 */

class SquerylUniqueValidator[T <: KeyedEntity[K], K](key: String, dao: SquerylDao[T,K], value: => K) extends Validator{
  def getKey = key

  def getReplaceModel = List[(String, Any)]()

  def isValid: Boolean = {
    if(dao.findById(value) == None) return true
    else return false
  }
}
