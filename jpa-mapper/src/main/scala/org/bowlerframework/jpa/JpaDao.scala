package org.bowlerframework.jpa

import org.bowlerframework.persistence.Dao
import com.recursivity.jpa.Jpa._
import com.recursivity.jpa.PersistenceUnit
import com.recursivity.commons.bean.GenericTypeDefinition

class JpaDao[T <: {def id: K}, K](persistenceUnit: String = PersistenceUnit.unitName)(implicit m : scala.Predef.Manifest[T], k: Manifest[K]) extends Dao[T,K]{
  private val typeString = m.toString.replace("[", "<").replace("]", ">")
  private val keyString = k.toString.replace("[", "<").replace("]", ">")
  private val typeDef = GenericTypeDefinition(typeString)
  private val keyDef = GenericTypeDefinition(keyString)
  def entityType = Class.forName(typeDef.clazz).asInstanceOf[Class[T]]


  var fieldCls: Class[_] = null
    keyDef.clazz match {
      case "Long" => fieldCls = classOf[Long]
      case "Int" => fieldCls = classOf[java.lang.Integer]
      case "Float" => fieldCls = classOf[java.lang.Float]
      case "Double" => fieldCls = classOf[java.lang.Double]
      case "Boolean" => fieldCls = classOf[Boolean]
      case "Short" => fieldCls = classOf[java.lang.Short]
      case _ => fieldCls = Class.forName(keyDef.clazz)
    }

  def keyType = fieldCls.asInstanceOf[Class[K]]

  def create(entity: T) = null

  def update(entity: T) = null

  def findAll(offset: Int = 0, results: Int = Integer.MAX_VALUE) = null

  def findById(id: K) = null


  def delete(entity: T) = null
}