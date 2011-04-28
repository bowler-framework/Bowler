package org.bowlerframework.view.scuery.component

import org.bowlerframework.view.scuery.Component
import org.bowlerframework.{RequestScope, Request}
import xml.NodeSeq

/**
 * Created by IntelliJ IDEA.
 * User: wfaler
 * Date: 26/03/2011
 * Time: 18:09
 * To change this template use File | Settings | File Templates.
 */

class ValidationFeedbackPanel(request: Request = RequestScope.request) extends Component {

  request.getSession.getErrors match {
    case None => {
      $("#errorSpan").contents = ""
    }
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

}

object ValidationFeedbackPanel {
  def showErrorMessages: NodeSeq = showErrorMessages(RequestScope.request)

  def showErrorMessages(request: Request): NodeSeq = {
    val panel = new ValidationFeedbackPanel(request)
    panel.render
  }
}