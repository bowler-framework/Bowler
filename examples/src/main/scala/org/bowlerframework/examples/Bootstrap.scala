package org.bowlerframework.examples

import org.bowlerframework.view.scalate._
import org.bowlerframework.view.scalate.selectors._

import org.bowlerframework.model.Validations
import org.bowlerframework.controller.Controller
import org.bowlerframework.view.Renderable
import collection.mutable.MutableList


class Bootstrap{
	TemplateRegistry.appendTemplateSelectors(List(new DefaultLayoutSelector(Layout("default"))))
	val controller = new SampleController	
}

class SampleController extends Controller with Validations with Renderable{
	
	// renders the root
	get("/")((req, resp) => {
		render
	
	})
	
	// just renders a form
	get("/form")((req, resp) => {
    	render
  	})

	/** POST that ALWAYS fails validation on purpose
	*/
  	post("/post")((req, resp) => {
    	validate({
      		val list = new MutableList[Tuple2[String, String]]
      		list += Tuple2("name", "name is mandatory")
      		list += Tuple2("age", "age must be over 18")
      		Some(list.toList)
    		})
		// further business logic and render/redirect would have gone here if validate returned None

  	})
}