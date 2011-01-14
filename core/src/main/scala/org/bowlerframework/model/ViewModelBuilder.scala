package org.bowlerframework.model

import org.bowlerframework.view.{ViewModel, ViewRenderer}
import collection.mutable.{WrappedArray, HashMap}

/**
 * Created by IntelliJ IDEA.
 * User: wfaler
 * Date: 14/01/2011
 * Time: 19:50
 * To change this template use File | Settings | File Templates.
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
    else
      return AliasRegistry.getModelAlias(model).get
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