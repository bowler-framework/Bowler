package org.bowlerframework.jpa

import org.bowlerframework.persistence.AbstractDao
import com.recursivity.jpa.Jpa._
import com.recursivity.jpa.PersistenceUnit
import scala.collection.JavaConverters._
import collection.mutable.MutableList

class JpaDao[T <: {def id: K}, K](persistenceUnit: String = PersistenceUnit.unitName)(implicit m : scala.Predef.Manifest[T], k: Manifest[K]) extends AbstractDao[T,K]{

  def create(entity: T) = {
    entityManager(persistenceUnit).persist(entity)
    entityManager(persistenceUnit).flush
  }

  def update(entity: T) = {
    entityManager(persistenceUnit).merge(entity)
    entityManager(persistenceUnit).flush
  }

  def findAll(offset: Int = 0, results: Int = Integer.MAX_VALUE): List[T] = {
    val query = entityManager(persistenceUnit).createQuery("FROM " + entityType.getSimpleName + " as bean ORDER BY bean." + defaultOrderBy)
    query.setFirstResult(offset)
    query.setMaxResults(results)
    val iter = query.getResultList.asInstanceOf[java.util.List[T]].iterator
    val list = new MutableList[T]
    while(iter.hasNext)
      list += iter.next
    list.toList
  }

  def defaultOrderBy: String = "id"


  def findById(id: K): Option[T] = {
    try{
      val entity = entityManager(persistenceUnit).find(this.entityType, id)
      if(entity == null)
        return None
      return Some(entity)
    }catch{
      case e: Exception => return None
    }
  }

  def delete(entity: T) = {
    val e = entityManager(persistenceUnit).find(this.entityType, entity.id)
    entityManager(persistenceUnit).remove(e)
    entityManager(persistenceUnit).flush
  }
}