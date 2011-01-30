package org.bowlerframework.squeryl

import org.squeryl.KeyedEntity
import com.recursivity.commons.bean.{TransformerRegistry, StringValueTransformer}

// add more keyed dao's here
class SquerylTransformer[T <: KeyedEntity[K], K](dao: SquerylDao[T,K]) extends StringValueTransformer[T]{

  def toValue(from: String): Option[T] = {
    return dao.findById(TransformerRegistry.resolveTransformer(dao.keyType).
      getOrElse(throw new IllegalArgumentException("no StringValueTransformer registered for type " + dao.keyType.getName)).toValue(from).asInstanceOf[K])
  }
}