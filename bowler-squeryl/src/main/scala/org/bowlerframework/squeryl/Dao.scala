package org.bowlerframework.squeryl

/**
 * Created by IntelliJ IDEA.
 * User: wfaler
 * Date: 09/02/2011
 * Time: 00:15
 * To change this template use File | Settings | File Templates.
 */

trait Dao[T <: {def id: K}, K]{
  def findById(id: K): Option[T]
  def delete(entity: T)
  def update(entity: T)
  def create(entity: T)
  def keyType: Class[K]
  def entityType: Class[T]
  def findAll(offset: Int = 0, results: Int = Integer.MAX_VALUE): List[T]
}