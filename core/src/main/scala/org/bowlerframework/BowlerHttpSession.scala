package org.bowlerframework

import javax.servlet.http.HttpSession
import collection.mutable.MutableList

/**
 * Created by IntelliJ IDEA.
 * User: wfaler
 * Date: 13/12/2010
 * Time: 00:09
 * To change this template use File | Settings | File Templates.
 */

class BowlerHttpSession(session: HttpSession) extends Session{
  def getId = session.getId

  def getAttributeNames: List[String] = {
    val list = new MutableList[String]
    val enum = session.getAttributeNames
    while(enum.hasMoreElements)
      list += enum.nextElement.toString
    return list.toList
  }

  def getCreationTime = session.getCreationTime

  def invalidate = {session.invalidate}

  def removeAttribute(name: String) = session.removeAttribute(name)

  def setAttribute(name: String, value: Any) = session.setAttribute(name, value)

  def getAttribute(name: String) = session.getAttribute(name)

  def getUnderlyingSession = session
}