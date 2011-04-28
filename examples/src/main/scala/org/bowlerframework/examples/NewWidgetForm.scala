package org.bowlerframework.examples

import org.bowlerframework.view.scuery.component.ValidationFeedbackPanel._
import org.bowlerframework.view.scuery.Component
import org.bowlerframework.RequestScope

/**
 * Created by IntelliJ IDEA.
 * User: wfaler
 * Date: 23/03/2011
 * Time: 22:53
 * To change this template use File | Settings | File Templates.
 */

class NewWidgetForm extends Component{
  val request = RequestScope.request
	
  $("#errorPanel").contents = showErrorMessages

  request.getSession.getValidatedModel match{
    case None => bindModel(Widget(0, null, null, null))
    case Some(seq) => bindModel(seq.head.asInstanceOf[Widget])
  }

  def bindModel(widget: Widget){
    $("form input[name='widget.id']").attribute("value", valueOrEmpty(widget.id))
    $("form input[name='widget.name']").attribute("value", valueOrEmpty(widget.name))
    $("form input[name='widget.yearMade']").attribute("value", valueOrEmpty(widget.yearMade))
    $("form input[name='widget.description']").attribute("value", valueOrEmpty(widget.description))
  }

}