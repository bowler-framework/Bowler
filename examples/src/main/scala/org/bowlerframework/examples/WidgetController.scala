package org.bowlerframework.examples

import org.bowlerframework.controller.{Controller, LayoutAware}
import org.bowlerframework.model.{ ParameterMapper, Validations}
import org.bowlerframework.view.{Renderable, ViewPath}
import org.bowlerframework.view.scalate.DefaultLayout
import org.bowlerframework._

/**
 * Our main application controller, showing a simple CRUD interface.
 *
 * extends:
 * - Controller: used to construct routes and deal with them by providing functions that respond to routes.
 * - ParameterMapper: takes a request and maps any values into beans or other objects.
 * - Validations: validation enables the Controller
 * - Renderable: allows you to render View Model objects.
 */

class WidgetController extends Controller with ParameterMapper with Validations with Renderable with LayoutAware {
  val parentLayout = DefaultLayout("default", "doLayout", None, Some(new ParentLayoutModel))
  // this is a childLayout for parentLayout, and has the parent set on it, as shown.
  val composableLayout = DefaultLayout("child", Some(parentLayout))

  // simple, no args render, just renders the root (or http 204 for JSON)
  get("/")((request, response) => render)

  def renderWidgets = {
     render(Widgets.findAll)
  }

  def renderComposable = {
	layout(composableLayout)
  	renderWith(ViewPath(GET, MappedPath("/widgets/")), Widgets.findAll)	
  }

  // renders the base routes
  get("/widgets")((request, response) => renderWidgets)
  get("/widgets/")((request, response) => renderWidgets)
  get("/composable")((request, response) => {renderComposable})
  get("/composable/")((request, response) => {renderComposable})


  // responds to GET with a named parameter (:id becomes named to id)
  get("/widgets/:id")((request, response) => {
    this.mapRequest[Option[Widget]](request)(widget => {
        render(widget)
    })
  })

  // responds to HTTP DELETE with named param
  delete("/widgets/:id")((request, response) => {
    this.mapRequest[Option[Widget]](request)(widget => {
      widget match{
        case Some(w) => Widgets.delete(widget.get)
        case None => response.sendError(500)
      }
    })
  })

  // retrieves an edit form for a widget that lets you edit a pre-existing widget.
  get("/widgets/:id/edit")((request, response) => {
    mapRequest[Option[Widget]](request)(widget => {
      render(widget)
    })
  })

  // form for creating a new Widget - passes in a new, empty widget to be filled out.
  get("/widgets/new")((request, response) => {render(Widget(0, null, null, null))})


    // form for creating a new Widget - passes in a new, empty widget to be filled out.
  get("/widgets/new/scuery")((request, response) => {renderWith(new ScueryWidgetPage(new NewWidgetForm),Widget(0, null, null, null))})

  // HTTP POST for creating new Widgets.
  post("/widgets")((request, response) =>{

    // Map the request into the resulting Widget..
    this.mapRequest[Widget](request)(widget => {

      // validate the Widget, pass the widget as a param, so the validation error functionality knows which object
      // failed validation, IF it fails.
      validate(widget){
        val validator = new WidgetValidator(widget)
        validator.add(new UniqueValidator({widget.id}))
        validator.validate
      }
      Widgets.create(widget)
      // send a redirect to the base page.
      response.sendRedirect("/widgets")
    })
  })


  // similar to the above POST, but for updating existing widgets.
  post("/widgets/:id")((request, response) => {
    this.mapRequest[Widget](request)(widget => {
      validate(widget){
        val validator = new WidgetValidator(widget)
        validator.validate
      }
      println("update")
      Widgets.update(widget)
      response.sendRedirect("/widgets")
    })
  })

}



