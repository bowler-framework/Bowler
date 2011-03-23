package org.bowlerframework.examples

import org.bowlerframework.view.squery.TransformerComponent
import org.bowlerframework.RequestScope

/**
 * Created by IntelliJ IDEA.
 * User: wfaler
 * Date: 23/03/2011
 * Time: 22:53
 * To change this template use File | Settings | File Templates.
 */

class NewWidgetForm extends TransformerComponent{
  val request = RequestScope.request

  request.getSession.getErrors match{
    case None => {$("#errorPanel").contents = ""}
    case Some(list) => {
      $("#errorPanel").contents(
          node => {
            list.flatMap {
              p =>
                transform(node.$("#errors")) {
                  $ =>
                    $("#error").contents = p._2
                }
            }
          }
        )
    }
  }

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

  def valueOrEmpty(any: Any): String = {
    if(any == null)
      return ""
    else return any.toString

  }

}