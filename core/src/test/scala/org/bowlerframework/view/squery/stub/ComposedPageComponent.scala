package org.bowlerframework.view.squery.stub

import org.bowlerframework.view.squery.{MarkupContainer, Component}

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