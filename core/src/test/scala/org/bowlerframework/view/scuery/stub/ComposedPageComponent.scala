package org.bowlerframework.view.scuery.stub

import org.bowlerframework.view.scuery.{MarkupContainer, Component}

/**
 * Created by IntelliJ IDEA.
 * User: wfaler
 * Date: 14/03/2011
 * Time: 00:26
 * To change this template use File | Settings | File Templates.
 */

class ComposedPageComponent(child: MarkupContainer) extends Component {
  $("body").contents = child.render
}