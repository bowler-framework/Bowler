package org.bowlerframework.squeryl

import org.squeryl.{Table, KeyedEntity}
import org.bowlerframework.model.{ParameterMapper, Validations}
import org.bowlerframework.view.Renderable
import com.recursivity.commons.bean.{TransformerRegistry, BeanUtils, GenericsParser}
import org.bowlerframework.{Response, Request}


/**
 * Created by IntelliJ IDEA.
 * User: wfaler
 * Date: 30/01/2011
 * Time: 03:42
 * To change this template use File | Settings | File Templates.
 */

abstract class CrudController[T <: KeyedEntity[K], K](resourceName: String, dao: SquerylDao[T, K]) extends SquerylController with Validations with ParameterMapper with Renderable{

  get("/" + resourceName + "/")((req, resp) =>{listResources(1, req, resp)})
  get("/" + resourceName)((req, resp) =>{listResources(1, req, resp)})
  get("/" + resourceName + "/page/:number")((req, resp) =>{listResources(req.getIntParameter("number"), req, resp)})

  get("/" + resourceName + "/:id")((req, resp) => render(dao.findById(parseId(req, "id"))))
  get("/" + resourceName + "/:id/edit")((req, resp) => render(dao.findById(parseId(req, "id"))))
  get("/" + resourceName + "/new")((req, resp) => render(BeanUtils.instantiate[T](dao.entityType)))

  delete("/widgets/:id")((request, response) => {
    dao.delete(parseId(request, "id"))
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

  def parseId(request: Request, idName: String): K = {
    return TransformerRegistry.resolveTransformer(dao.keyType).
      getOrElse(throw new IllegalArgumentException("no StringValueTransformer registered for type " + dao.keyType.getName)).toValue(request.getStringParameter(idName)).asInstanceOf[K]
  }

  def listResources(page: Int, request: Request, response: Response) = {
    val items = request.getSession.getAttribute[Int]("_bowlerListItems")
    if(items == None)
      request.getSession.setAttribute("_bowlerListItems", 10)
    val offset = page * items.getOrElse(10)
    val maxItems = items.getOrElse(10)

    render(dao.findAll(offset, maxItems))
  }

}