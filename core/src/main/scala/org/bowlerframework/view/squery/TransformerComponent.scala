package org.bowlerframework.view.squery

import org.fusesource.scalate.scuery.Transformer

/**
 * Created by IntelliJ IDEA.
 * User: wfaler
 * Date: 12/03/2011
 * Time: 18:52
 * To change this template use File | Settings | File Templates.
 */

abstract class TransformerComponent extends Transformer with Component{
  override def render = this.apply(super.render)
}