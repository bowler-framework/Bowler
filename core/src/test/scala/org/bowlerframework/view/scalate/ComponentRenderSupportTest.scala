package org.bowlerframework.view.scalate

import org.scalatest.FunSuite
import java.io.{PrintWriter, StringWriter}

/**
 * Created by IntelliJ IDEA.
 * User: wfaler
 * Date: 23/01/2011
 * Time: 02:17
 * To change this template use File | Settings | File Templates.
 */

class ComponentRenderSupportTest extends FunSuite {
  test("simple component") {
    println(SimpleComponent.show)
    assert("A Foo is a bar" == SimpleComponent.show)
  }

  test("complex nexted renderable with model around") {
    RenderEngine.reset
    val uri = "/org/bowlerframework/view/scalate/complex.ssp"
    val model = Map("bean" -> Widget("hello", 50), "bean2" -> Widget("bye", 100), "tuple" -> Tuple2("foo", "bar"))
    val writer = new StringWriter
    val pw = new PrintWriter(writer)
    val context = new BowlerRenderContext(uri, RenderEngine.getEngine, pw)

    context.render(uri, model)
    println(writer.toString)
    assert(writer.toString == "hello 50 - A Foo is a bar bye 100")

  }


  test("with varargs (a la renderable)") {
    assert("A Foo is a bar" == SimpleComponent.show(Tuple2("foo", "bar")))
  }
}

object SimpleComponent extends ComponentRenderSupport {

  def show: String = {
    this.renderMap(Map("foo" -> "bar"))
  }

  def show(any: Any) = {
    this.render(any)
  }
}

case class Widget(name: String, price: Int)

