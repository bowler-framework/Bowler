package org.bowlerframework.view.scalate

import org.scalatest.FunSuite

/**
 * Created by IntelliJ IDEA.
 * User: wfaler
 * Date: 23/01/2011
 * Time: 02:17
 * To change this template use File | Settings | File Templates.
 */

class ComponentRenderSupportTest extends FunSuite{
  test("simple component"){
    println(SimpleComponent.show)
    assert("A Foo is a bar" == SimpleComponent.show)
  }

  test("complext nexted renderable with model around"){

  }

  test("with multiple items in model"){

  }

  test("with varargs (a la renderable)"){

  }
}

object SimpleComponent extends ComponentRenderSupport{

  def show: String = {
    this.render(Map("foo" -> "bar"))
  }
}

