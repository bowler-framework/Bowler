package org.bowlerframework.view.scalate

import org.scalatra.test.scalatest.ScalatraFunSuite
import org.bowlerframework.model.Validations
import org.bowlerframework.controller.Controller
import org.bowlerframework.view.Renderable
import selectors.DefaultLayoutSelector
import collection.mutable.MutableList
import org.bowlerframework.http.BowlerServlet

/**
 * Created by IntelliJ IDEA.
 * User: wfaler
 * Date: 14/01/2011
 * Time: 00:47
 * To change this template use File | Settings | File Templates.
 */

class ScalateViewRendererErrorHandlingTest extends ScalatraFunSuite{
  TemplateRegistry.reset
  TemplateRegistry.appendTemplateSelectors(List(new DefaultLayoutSelector(Layout("simple"))))


  test("send redirect errors"){
    this.addServlet(new BowlerServlet, "/*")
    val controller = new ErrorHandlingController
    //
   // this.start
   /*
    //this.asInstanceOf
    get("/form"){
      assert("<div><form method=\"POST\" action=\"/post\"><input name=\"name\"/><input type=\"submit\"/></form></div>" == this.body)

      post("/form"){
        println("POST BODY: ")
        println(body)

        get("/form"){
          println("ASSERT FORM: ")
          assert("<div><form method=\"POST\" action=\"/post\"><input name=\"name\"/><input type=\"submit\"/></form></div>" == this.body)
        }
      }
    }
    //this.stop

  }    */
  }


}

class ErrorHandlingController extends Controller with Validations with Renderable{

  get("/form")((req, resp) => {

    render
  })

  post("/form")((req, resp) => {
    validate({
      val list = new MutableList[Tuple2[String, String]]
      list += Tuple2("name", "name is mandatory")
      list += Tuple2("age", "age must be over 18")
      Some(list.toList)
    })

  })
}