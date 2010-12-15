package org.bowlerframework.controller

import org.bowlerframework.RequestScope


class MyController extends Controller with Validations{
  var i = 0
  var response: String = null

  // response to HTTP GET at "/myController"
  // testing composition with function for mapping request params to Int
  get("/myController")(mapRequest[Int](a => {
    i = i + a
    response = RequestScope.request.getStringParameter("name") + i
    println(response)
  }))


  // handles HTTP POST to "/somePost"
  post("/somePost"){
    validate{
      if(RequestScope.request.getParameterNames.exists(p => p.equals("name")))
        None
      else{
        val error = ("name", "Name is a required parameter!")
        Some(List(error))  // execution of the post() should stop after this and go to onValidationErrors
      }
    }
    response = "success!"
  }


  // impl of Validations trait function
  def onValidationErrors(errors: List[(String, String)]) = {
    response = ""
    errors.foreach(tup => {
      response = tup._1 + ":" + tup._2
    })
  }


  // just a simple test of method signature for request mapping to Int (or any other type).
  // Doesn't actually map/transform anything
  def mapRequest[R](func: R => Any)(implicit m: Manifest[R]): Unit ={

    println(func)
    func(Integer.parseInt("1").asInstanceOf[R])
  }
}