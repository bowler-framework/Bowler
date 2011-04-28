package org.bowlerframework.view.scuery.stub

import org.bowlerframework.view.scuery.Component

/**
 * Created by IntelliJ IDEA.
 * User: wfaler
 * Date: 14/03/2011
 * Time: 00:34
 * To change this template use File | Settings | File Templates.
 */

abstract class InheritanceCompositionComponent extends Component {
  $("body").contents = child.render

  def child: Component
}

class ConcreteInheritanceCompositionComponent extends InheritanceCompositionComponent {
  def child = new SimpleTransformingComponent
}