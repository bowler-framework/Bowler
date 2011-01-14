package org.bowlerframework.examples

import collection.mutable.MutableList
import org.bowlerframework.controller.Controller
import com.recursivity.commons.bean.{TransformerRegistry, StringValueTransformer}
import org.bowlerframework.model.{AliasRegistry, ParameterMapper, Validations}
import org.bowlerframework.view.{ViewModel, Renderable}

/**
 * Created by IntelliJ IDEA.
 * User: wfaler
 * Date: 14/01/2011
 * Time: 18:05
 * To change this template use File | Settings | File Templates.
 */

class WidgetController extends Controller with ParameterMapper with Validations with Renderable {


  // simple, no args render, just renders the root (or http 204 for JSON)
  get("/")((request, response) => render)

  def renderWidgets = {
    val widgets = Widgets.findAll
    if(widgets.size == 0)
      render
    else
      render(widgets)
  }


  get("/widgets")((request, response) => renderWidgets)
  get("/widgets/")((request, response) => renderWidgets)

  get("/widgets/:id")((request, response) => {
    this.mapRequest[Option[Widget]](request)(widget => {
      if(widget != None)
        render(widget.get)
      else render
    })
  }
  )

  delete("/widgets/:id")((request, response) => {
    this.mapRequest[Option[Widget]](request)(widget => {
      if(widget != None)
        Widgets.delete(widget.get)
      else
        response.sendError(500)
    })
  }
  )

  get("/widgets/:id/edit")((request, response) => {
    mapRequest[Option[Widget]](request)(widget => {
      if(widget != None)
        render(widget.get)
      else render
    })
  })

  get ("/widgets/new")((request, response) => {render})

  post("/widgets")((request, response) =>{
    println(request.getParameterMap)
    this.mapRequest[Widget](request)(widget => {
       Widgets.update(widget)
      response.sendRedirect("/widgets")
    })
  })

  post("/widgets/:id")((request, response) => {

    this.mapRequest[Widget](request)(widget => {
      Widgets.update(widget)
      response.sendRedirect("/widgets")
    })
  })

}



