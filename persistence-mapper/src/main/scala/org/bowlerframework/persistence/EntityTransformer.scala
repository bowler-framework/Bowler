package org.bowlerframework.persistence

import com.recursivity.commons.bean.{TransformerRegistry, StringValueTransformer}


class EntityTransformer[T <: {def id: K}, K](dao: Dao[T,K]) extends StringValueTransformer[T]{

  def toValue(from: String): Option[T] = {
    val key = TransformerRegistry(dao.keyType).
      getOrElse(throw new IllegalArgumentException("no StringValueTransformer registered for type " + dao.keyType.getName)).toValue(from)
    if(key == None)
      throw new IllegalArgumentException("cannot transform value " + from  + " with StringValueTransformer registered for type " + dao.keyType.getName)
    return dao.findById(key.get.asInstanceOf[K])
  }
}