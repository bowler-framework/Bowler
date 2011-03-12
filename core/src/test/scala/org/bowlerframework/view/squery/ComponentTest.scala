package org.bowlerframework.view.squery

import org.scalatest.FunSuite
import stub.MySimpleComponent

/**
 * Created by IntelliJ IDEA.
 * User: wfaler
 * Date: 12/03/2011
 * Time: 18:56
 * To change this template use File | Settings | File Templates.
 */

class ComponentTest extends FunSuite{

  test("get classpath resource"){
    println((new MySimpleComponent).render)

  }
}