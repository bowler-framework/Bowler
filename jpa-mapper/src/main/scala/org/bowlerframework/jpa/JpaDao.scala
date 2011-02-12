package org.bowlerframework.jpa

import org.bowlerframework.persistence.AbstractDao
import com.recursivity.jpa.Jpa._
import com.recursivity.jpa.PersistenceUnit
import com.recursivity.commons.bean.GenericTypeDefinition

class JpaDao[T <: {def id: K}, K](persistenceUnit: String = PersistenceUnit.unitName)(implicit m : scala.Predef.Manifest[T], k: Manifest[K]) extends AbstractDao[T,K]{

  def create(entity: T) = null

  def update(entity: T) = null

  def findAll(offset: Int = 0, results: Int = Integer.MAX_VALUE) = null

  def findById(id: K) = null

  def delete(entity: T) = null
}