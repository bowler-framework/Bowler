package org.bowlerframework.model

import org.bowlerframework.view.{ViewModel, ViewRenderer}
import collection.mutable.{WrappedArray, HashMap}

/**
 * Used internally to map from a Seq of view models into something useful for Scalate.
 */

object ViewModelBuilder{
  def buildModel(models: Seq[Any]): HashMap[String,Any] ={
    val model = new HashMap[String, Any]
    models.foreach(f =>{

      val alias = getModelAlias(unwrap(f))
      val value = getModelValue(unwrap(f))
      model.put(alias, value)
    })
    return model
  }


  private def unwrap(model: Any): Any = {
    if(model.isInstanceOf[Option[_]]){
      if(model != None)
        return model.asInstanceOf[Option[_]].get
      else
        return ""
    }
    return model
  }

  private def getModelAlias(model: Any): String = {
    if(model.isInstanceOf[ViewModel])
      return model.asInstanceOf[ViewModel].alias
    else if(model.isInstanceOf[Tuple2[String, _]])
      return model.asInstanceOf[Tuple2[String, _]]._1
    else{
      if(model != None){
        val alias = AliasRegistry.getModelAlias(model)
        if(alias != None)
          return alias.get
        else return ""

      } else return ""
    }
  }

  private def getModelValue(model: Any): Any = {
    if(model.isInstanceOf[ViewModel])
      return model.asInstanceOf[ViewModel].value
    else if(model.isInstanceOf[Tuple2[String, _]])
      return model.asInstanceOf[Tuple2[String, Any]]._2
    else
      return model
  }
}