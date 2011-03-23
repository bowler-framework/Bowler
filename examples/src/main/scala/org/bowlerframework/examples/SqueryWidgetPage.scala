package org.bowlerframework.examples

import org.bowlerframework.view.squery.{Component, TransformerComponent}

/**
 * Created by IntelliJ IDEA.
 * User: wfaler
 * Date: 23/03/2011
 * Time: 22:49
 * To change this template use File | Settings | File Templates.
 */

class SqueryWidgetPage(component: Component) extends TransformerComponent{

  $(".tabs-container").contents = component.render

}