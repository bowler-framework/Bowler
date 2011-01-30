package org.bowlerframework.squeryl

import org.squeryl.{KeyedEntity}
import org.bowlerframework.model.{ParameterMapper, Validations}
import org.bowlerframework.view.Renderable
import com.recursivity.commons.bean.{TransformerRegistry, BeanUtils}
import org.bowlerframework.{Response, Request}


/**
 * Created by IntelliJ IDEA.
 * User: wfaler
 * Date: 30/01/2011
 * Time: 03:42
 * To change this template use File | Settings | File Templates.
 */

abstract class CrudController[T <: KeyedEntity[K], K](dao: SquerylDao[T, K], resourceName: String)(implicit m : scala.Predef.Manifest[T]) extends SquerylController with Validations with ParameterMapper with Renderable{
  val transformer = new SquerylTransformer[T, K](dao)
  TransformerRegistry.registerSingletonTransformer(dao.entityType, transformer)

  get("/" + resourceName + "/")((req, resp) =>{listResources(1, req, resp)})
  get("/" + resourceName)((req, resp) =>{listResources(1, req, resp)})
  get("/" + resourceName + "/page/:number")((req, resp) =>{listResources(req.getIntParameter("number"), req, resp)})

  get("/" + resourceName + "/new")((req, resp) => render(BeanUtils.instantiate[T](dao.entityType)))
  get("/" + resourceName + "/:id")((req, resp) => renderBean(req, resp))
  get("/" + resourceName + "/:id/edit")((req, resp) => renderBean(req, resp))

  delete("/widgets/:id")((request, response) => {
    this.mapRequest[T](request)(bean => {
      dao.delete(bean)
    })
  })


  // HTTP POST for creating new Widgets.
  post("/" + resourceName + "/")((request, response) =>{

  })

  put("/" + resourceName + "/")((request, response) =>{

  })

  // similar to the above POST, but for updating existing widgets.
  post("/" + resourceName + "/:id")((request, response) => {

  })

  put("/" + resourceName + "/:id")((request, response) => {

  })


  def renderBean(request: Request, response: Response){
    this.mapRequest[Option[T]](request)(bean => {
      render(bean)
    })
  }


  def parseId(request: Request, idName: String): K = {
    return TransformerRegistry.resolveTransformer(dao.keyType).
      getOrElse(throw new IllegalArgumentException("no StringValueTransformer registered for type " + dao.keyType.getName)).toValue(request.getStringParameter(idName)).asInstanceOf[K]
  }

  def listResources(page: Int, request: Request, response: Response) = {
    try{
      request.getSession.setAttribute("_bowlerListItems", request.getIntParameter("itemsInList"))
    }catch{
      case e: Exception => {} // do nothing, fallback to default behavior
    }

    val items = request.getSession.getAttribute[Int]("_bowlerListItems")
    // add check for request param of "itemsInList"
    if(items == None)
      request.getSession.setAttribute("_bowlerListItems", 10)
    val offset = page * items.getOrElse(10)
    val maxItems = items.getOrElse(10)

    render(dao.findAll(offset, maxItems))
  }

}