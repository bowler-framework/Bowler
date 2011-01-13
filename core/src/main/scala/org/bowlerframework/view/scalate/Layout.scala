package org.bowlerframework.view.scalate

import org.bowlerframework.Request

/**
 * Created by IntelliJ IDEA.
 * User: wfaler
 * Date: 03/01/2011
 * Time: 22:44
 * To change this layout use File | Settings | File Templates.
 */

case class Layout(name: String, parentLayout: Option[Layout] = None, layoutModel: LayoutModel = new NoopLayoutModel)