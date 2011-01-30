package org.bowlerframework.squeryl.dao

import org.squeryl.{Table, KeyedEntity}
import org.bowlerframework.squeryl.SquerylDao
import org.squeryl.PrimitiveTypeMode._
/**
 * Created by IntelliJ IDEA.
 * User: wfaler
 * Date: 30/01/2011
 * Time: 15:40
 * To change this template use File | Settings | File Templates.
 */

class StringKeyedDao[T <: KeyedEntity[String]](table: Table[T])(implicit m : scala.Predef.Manifest[T]) extends SquerylDao[T, String](table){
  def findById(id: String): Option[T] = {
    try{
      Some(table.where(f => f.id === id).single)
    }catch{
      case e: Exception => return None
    }
  }

  override def findAll(offset: Int = 0, results: Int = Integer.MAX_VALUE) = from(table)(a => select(a) orderBy(a.id asc)).page(offset, results).toList
}