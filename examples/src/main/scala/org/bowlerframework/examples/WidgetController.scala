package org.bowlerframework.examples

import org.bowlerframework.model.Validations
import org.bowlerframework.view.{Renderable, ViewPath}
import org.bowlerframework.view.scalate.DefaultLayout
import org.bowlerframework._
import controller.{FunctionNameConventionRoutes, Controller, LayoutAware}

/**
 * Our main application controller, showing a simple CRUD interface.
 *
 * extends:
 * - Controller: used to construct routes and deal with them by providing functions that respond to routes.
 * - FunctionNameConventionRoutes: self-typed to Controller, allows mapping by function-name convention
 * - Validations: validation enables the Controller
 * - Renderable: allows you to render View Model objects.
 */

class WidgetController extends Controller with FunctionNameConventionRoutes with Validations with Renderable with LayoutAware {
  val parentLayout = DefaultLayout("default", "doLayout", None, Some(new ParentLayoutModel))
  // this is a childLayout for parentLayout, and has the parent set on it, as shown.
  val composableLayout = DefaultLayout("child", Some(parentLayout))

  def renderComposable = {
    layout(composableLayout)
    renderWith(ViewPath(GET, MappedPath("/widgets/")), Widgets.findAll)
  }

  def `GET /widgets` = render(Widgets.findAll)

  def `GET /widgets/` = render(Widgets.findAll)

  def `GET /composable` = renderComposable

  def `GET /composable/` = renderComposable

  def `GET /` = render

  // responds to GET with a named parameter (:id becomes named to id)
  def `GET /widgets/:id`(widget: Option[Widget]) = render(widget)

  // retrieves an edit form for a widget that lets you edit a pre-existing widget.
  def `GET /widgets/:id/edit`(widget: Option[Widget]) = render(widget)

  // form for creating a new Widget - passes in a new, empty widget to be filled out.
  def `GET /widgets/new` = render(Widget(0, null, null, null))

  // form for creating a new Widget - passes in a new, empty widget to be filled out.
  def `GET /widgets/new/scuery` = renderWith(new ScueryWidgetPage(new NewWidgetForm), Widget(0, null, null, null))


  // responds to HTTP DELETE with named param
  def `DELETE /widgets/:id`(widget: Option[Widget]) = {
    widget match {
      case Some(w) => Widgets.delete(widget.get)
      case None => RequestScope.response.sendError(500)
    }
  }

  // HTTP POST for creating new Widgets.
  def `POST /widgets`(widget: Widget) = {
    println("POST RECEIVED")
    // validate the Widget, pass the widget as a param, so the validation error functionality knows which object
    // failed validation, IF it fails.
    validate(widget) {
      val validator = new WidgetValidator(widget)
      validator.add(new UniqueValidator({
        widget.id
      }))
      validator.validate
    }
    Widgets.create(widget)
    // send a redirect to the base page.
    RequestScope.response.sendRedirect("/widgets")
  }

  // similar to the above POST, but for updating existing widgets.
  def `POST /widgets/:id`(widget: Widget) = {
    validate(widget) {
      val validator = new WidgetValidator(widget)
      validator.validate
    }
    Widgets.update(widget)
    RequestScope.response.sendRedirect("/widgets")
  }
}



