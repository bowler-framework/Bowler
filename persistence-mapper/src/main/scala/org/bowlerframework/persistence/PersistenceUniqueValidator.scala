package org.bowlerframework.persistence

import com.recursivity.commons.validator.Validator

/**
 * Created by IntelliJ IDEA.
 * User: wfaler
 * Date: 30/01/2011
 * Time: 15:30
 * To change this template use File | Settings | File Templates.
 */

class PersistedUniqueValidator[T <: {def id: K}, K](key: String, dao: Dao[T,K], value: => K) extends Validator{
  def getKey = key

  def getReplaceModel = List[(String, Any)]()

  def isValid: Boolean = {
    if(dao.findById(value) == None) return true
    else return false
  }
}