package org.bowlerframework.view

import org.scalatest.FunSuite

/**
 * Created by IntelliJ IDEA.
 * User: wfaler
 * Date: 28/12/2010
 * Time: 20:52
 * To change this template use File | Settings | File Templates.
 */

class RenderableTest extends FunSuite{

  test("varags"){
    render(5)
    render(5, "hello", List("hello"))

  }

  def render(models: Any*){
    render("200", models)

  }

  def render(responseCode: String, models: Any*){
    println("RESPONSE CODE: " + responseCode)
    models.foreach(f => println(f))

  }
}