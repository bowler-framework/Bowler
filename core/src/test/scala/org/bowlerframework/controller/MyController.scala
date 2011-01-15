package org.bowlerframework.controller

import org.bowlerframework.{Request, Response, RequestScope}
import org.bowlerframework.model.Validations

class MyController extends Controller with Validations{
  var i = 0
  var responseString: String = null

  // response to HTTP GET at "/myController"
  // testing composition with function for mapping request params to Int
  get("/myController")((request, response) => {

    mapRequest[Int](request)(a => {
      i = i + a
      // RequestScope allows access to Request/Response via dynamic variable
      responseString = request.getStringParameter("name") + i

      println(responseString)
    })
  })


  // handles HTTP POST to "/somePost"
  post("/somePost")((request, response) => {

    validate(None){
      if(RequestScope.request.getParameterNames.exists(p => p.equals("name")))
        None
      else{
        // errors defined as list of key/message pairs
        val error = ("name", "Name is a required parameter!")
        Some(List(error))  // execution of the post() should stop after this and go to onValidationErrors
      }
    }
    responseString = "success!"
  })


  // impl of Validations trait function
  def onValidationErrors(errors: List[(String, String)]) = {
    responseString = ""
    errors.foreach(tup => {
      responseString = tup._1 + ":" + tup._2
    })
  }


  // just a simple test of method signature for request mapping to Int (or any other type).
  // Doesn't actually map/transform anything
  def mapRequest[R](request: Request)(func: R => Any)(implicit m: Manifest[R]): Unit ={

    println(func)
    func(Integer.parseInt("1").asInstanceOf[R])
  }
}