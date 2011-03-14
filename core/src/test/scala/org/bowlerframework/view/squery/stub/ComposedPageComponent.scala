package org.bowlerframework.view.squery.stub

import org.bowlerframework.view.squery.{Component, TransformerComponent}

/**
 * Created by IntelliJ IDEA.
 * User: wfaler
 * Date: 14/03/2011
 * Time: 00:26
 * To change this template use File | Settings | File Templates.
 */

class ComposedPageComponent(child: Component) extends TransformerComponent{
  $("body").contents = child.render
}