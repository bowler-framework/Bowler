package org.bowlerframework.model

import org.scalatest.FunSuite

/**
 * Created by IntelliJ IDEA.
 * User: wfaler
 * Date: 14/01/2011
 * Time: 19:54
 * To change this template use File | Settings | File Templates.
 */

class ViewModelBuilderTest extends FunSuite{

  test("widget"){
    val model = vararg(new Widget(1, "wille"))
    println(model)
  }

  test("some(widget)"){
    val model = vararg(Some(new Widget(1, "wille")))
    println(model)
  }

  def vararg(model: Any*) = ViewModelBuilder(model.toSeq)

}

case class Widget(id: Long, name: String)