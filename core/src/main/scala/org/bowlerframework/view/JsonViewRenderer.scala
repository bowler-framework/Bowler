package org.bowlerframework.view

import org.bowlerframework.{Response, Request}
import net.liftweb.json.JsonAST._
import net.liftweb.json.Extraction._
import net.liftweb.json.Printer._
import org.bowlerframework.model.AliasRegistry

/**
 * Created by IntelliJ IDEA.
 * User: wfaler
 * Date: 28/12/2010
 * Time: 22:40
 * To change this layout use File | Settings | File Templates.
 */

class JsonViewRenderer extends ViewRenderer{
  implicit val formats = net.liftweb.json.DefaultFormats

  def onError(request: Request, response: Response, exception: Exception) = null

  def renderView(request: Request, response: Response, models: Any*) = {
    if(models.size == 0){
      response.setStatus(204)
    }else if(models.size == 1){
      models.foreach(f =>{
        response.getWriter.write(compact(render(decompose(f))))
      })
    }else{
      var json: JValue = null
      models.foreach(f =>{
        val alias = getAlias(f)
        val value = getValue(f)
        if(json == null) json = new JField(alias, value)
        else json = json ++ JField(alias, value)
      })
      response.getWriter.write(compact(render(json)))
    }
  }

  def renderView(request: Request, response: Response) = response.setStatus(204)

  private def getAlias(any: Any): String = {
    if(any.isInstanceOf[ViewModel])
      return any.asInstanceOf[ViewModel].alias
    else if(any.isInstanceOf[Tuple2[String, _]])
      return any.asInstanceOf[Tuple2[String, _]]._1
    else
      return AliasRegistry.getModelAlias(any).get
  }

  private def getValue(any: Any): JValue = {
    if(any.isInstanceOf[ViewModel])
      return decompose(any.asInstanceOf[ViewModel].value)
    else if(any.isInstanceOf[Tuple2[String, _]])
      return decompose(any.asInstanceOf[Tuple2[String, Any]]._2)
    else
      return decompose(any)
  }
}