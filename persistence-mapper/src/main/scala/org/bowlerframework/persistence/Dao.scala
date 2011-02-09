package org.bowlerframework.persistence

trait Dao[T <: {def id: K}, K]{
  def findById(id: K): Option[T]
  def delete(entity: T)
  def update(entity: T)
  def create(entity: T)
  def keyType: Class[K]
  def entityType: Class[T]
  def findAll(offset: Int = 0, results: Int = Integer.MAX_VALUE): List[T]
}