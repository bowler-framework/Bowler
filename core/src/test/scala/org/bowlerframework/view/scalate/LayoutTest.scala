package org.bowlerframework.view.scalate

import org.scalatest.FunSuite
import org.bowlerframework.jvm.DummyRequest
import org.bowlerframework.{GET, Request}

/**
 * Created by IntelliJ IDEA.
 * User: wfaler
 * Date: 24/04/2011
 * Time: 03:51
 * To change this template use File | Settings | File Templates.
 */

class LayoutTest extends FunSuite{
  val parentLayout = DefaultLayout("default")

  def resolver(request: Request): Option[Layout] = Option(parentLayout)
  TemplateRegistry.defaultLayout = resolver(_)

  test("layout bug - double nested Some()"){
    Layout.activeLayout(new DummyRequest(GET, "/LayoutTest", Map(), null)) match{
      case Some(layout) => assert(layout.asInstanceOf[DefaultLayout].name == "default")
      case None => fail
    }
  }

}