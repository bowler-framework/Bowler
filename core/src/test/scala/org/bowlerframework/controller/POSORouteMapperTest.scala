package org.bowlerframework.controller

import org.scalatest.FunSuite
import com.recursivity.commons.bean.scalap.ClassSignature

/**
 * Created by IntelliJ IDEA.
 * User: wfaler
 * Date: 01/05/2011
 * Time: 02:48
 * To change this template use File | Settings | File Templates.
 */

class POSORouteMapperTest extends FunSuite{

  test("strange function names"){
    //
    this.getClass.getMethods.foreach(f => println(f.getName))
  }


  def `GET /_hello/:id *` = {
    println("hello")
  }
}

// space = $u0020
// / = $div
// : = $colon
// * = $times