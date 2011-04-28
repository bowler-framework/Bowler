package org.bowlerframework.view.scuery

import org.fusesource.scalate.scuery.Transformer

/**
 * Created by IntelliJ IDEA.
 * User: wfaler
 * Date: 12/03/2011
 * Time: 18:52
 * To change this template use File | Settings | File Templates.
 */

abstract class Component extends Transformer with MarkupContainer {
  override def render = this.apply(super.render)

  def valueOrEmpty(any: Any): String = {
    if (any == null)
      return ""
    else return any.toString
  }
}