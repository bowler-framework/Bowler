package org.bowlerframework.view.squery

import org.scalatest.FunSuite
import stub.{ExtendingComponent, MySimpleComponent}

/**
 * Created by IntelliJ IDEA.
 * User: wfaler
 * Date: 12/03/2011
 * Time: 18:56
 * To change this template use File | Settings | File Templates.
 */

class ComponentTest extends FunSuite{

  test("get classpath resource"){
    val result = (new MySimpleComponent).render
    assert("A Title" == (result \\ "title").text)
  }

  test("get inherited markup"){
    val result = (new ExtendingComponent).render
    assert("A Title" == (result \\ "title").text)
  }
}