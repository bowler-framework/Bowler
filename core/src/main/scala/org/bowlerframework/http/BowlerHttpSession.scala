package org.bowlerframework.http

import javax.servlet.http.HttpSession
import collection.mutable.MutableList
import org.bowlerframework.Session

/**
 * Created by IntelliJ IDEA.
 * User: wfaler
 * Date: 13/12/2010
 * Time: 00:09
 * To change this layout use File | Settings | File Templates.
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

  def setAttribute[T](name: String, value: T) = session.setAttribute(name, value)

  def getAttribute[T](name: String): Option[T] = {
    if(session.getAttribute(name) != null)
      return Some(session.getAttribute(name).asInstanceOf[T])
    else
      return None
  }

  def getUnderlyingSession = session
}