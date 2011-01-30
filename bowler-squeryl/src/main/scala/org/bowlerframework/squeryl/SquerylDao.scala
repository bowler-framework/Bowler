package org.bowlerframework.squeryl

import org.squeryl.{KeyedEntity, Table}
import org.squeryl.PrimitiveTypeMode._
import com.recursivity.commons.bean.{GenericsParser}
import org.squeryl.dsl.QueryYield

/**
 * Created by IntelliJ IDEA.
 * User: wfaler
 * Date: 30/01/2011
 * Time: 03:50
 * To change this template use File | Settings | File Templates.
 */

abstract class SquerylDao[T <: KeyedEntity[K], K](table: Table[T])(implicit m : scala.Predef.Manifest[T]){
  private val typeString = m.toString.replace("[", "<").replace("]", ">")
  private val typeDef = GenericsParser.parseDefinition(typeString)
  def entityType = Class.forName(typeDef.clazz)
  def keyType = Class.forName(typeDef.genericTypes.get.head.clazz)

  def create(entity: T) = table.insert(entity)

  def update(entity: T) = table.update(entity)

  def findAll(offset: Int = 0, results: Int = Integer.MAX_VALUE) = from(table)(a => select(a)).page(offset, results).toList

  //def orderByClause: QueryYield[_]

  def findById(id: K): Option[T]

  def delete(entity: T) = table.delete(entity.id)
}



