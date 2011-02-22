package org.bowlerframework.view.scalate

import org.fusesource.scalate.DefaultRenderContext
import org.scalatest.FunSuite
import org.fusesource.scalate.util.{FileResourceLoader, Resource}
import java.io.{StringWriter, PrintWriter}



/**
 * Created by IntelliJ IDEA.
 * User: wfaler
 * Date: 04/01/2011
 * Time: 22:27
 * To change this layout use File | Settings | File Templates.
 */

class ScalateRenderEngineTest extends FunSuite{

  test("default test numberformat"){
    val model = Map("id" -> 2300)
    val engine = RenderEngine.getEngine
    val uri =  "ScalateRenderEngineNFTest.mustache"
    engine.resourceLoader = new FileResourceLoader {
      override def resource(uri: String): Option[Resource] = {

        Some(Resource.fromText(uri, "Number is: {{id}}"))
      }
    }
    val writer = new StringWriter
    val pw = new PrintWriter(writer)
    val context = new DefaultRenderContext(uri, engine, pw)

    context.render(uri, model)

    assert(writer.toString.equals("Number is: 2,300"))
    RenderEngine.reset

  }


  test("multiple Mustache id's with same id"){
    val model = Map("id" -> "someId")
    val engine = RenderEngine.getEngine
    val uri =  "ScalateRenderEngineTest.mustache"
    engine.resourceLoader = new FileResourceLoader {
      override def resource(uri: String): Option[Resource] = {

        Some(Resource.fromText(uri, "hello {{id}}, your name is {{id}}"))
      }
    }
    val writer = new StringWriter
    val pw = new PrintWriter(writer)
    val context = new DefaultRenderContext(uri, engine, pw)

    context.render(uri, model)

    assert(writer.toString.equals("hello someId, your name is someId"))
    RenderEngine.reset
  }

  test("test link syntax"){
    val model = Map[String, Any]()
    val engine = RenderEngine.getEngine
    val uri =  "ScalateRenderEngineTestLinkTest.mustache"
    engine.resourceLoader = new FileResourceLoader {
      override def resource(uri: String): Option[Resource] = {
        if(!uri.contains("link:/basePath"))
          Some(Resource.fromText(uri, "<a href='{{>link:/basePath}}'>Google</a>"))
        else
          Some(Resource.fromText(uri, "http://www.google.com"))
      }
    }
    val writer = new StringWriter
    val pw = new PrintWriter(writer)
    val context = new DefaultRenderContext(uri, engine, pw)

    context.render(uri, model)
    assert(writer.toString.equals("<a href='http://www.google.com'>Google</a>"))
    RenderEngine.reset
  }

  test("test ssp"){
    val bean: Any = ScalateBean("wille")
    val model = Map("bean" -> bean)

    val engine = RenderEngine.getEngine
    val template = "<% import org.bowlerframework.view.scalate.ScalateBean %>" +
      "<%@ val bean: ScalateBean %>" +
      "Hello ${bean.name}"
    val uri =  "typeSafety.ssp"
    engine.resourceLoader = new FileResourceLoader {
      override def resource(uri: String): Option[Resource] = {
          Some(Resource.fromText(uri, template))
      }
    }
    val writer = new StringWriter
    val pw = new PrintWriter(writer)
    val context = new DefaultRenderContext(uri, engine, pw)

    context.render(uri, model)
    assert("Hello wille" == writer.toString)
    RenderEngine.reset
  }

  test("mustache bean"){
    val bean: Any = ScalateBean("wille")
    val model = Map("bean" -> bean)

    val engine = RenderEngine.getEngine
    val template = "{{#bean}}hello {{name}}{{/bean}}"
    val uri =  "typeSafety.mustache"
    engine.resourceLoader = new FileResourceLoader {
      override def resource(uri: String): Option[Resource] = {
          Some(Resource.fromText(uri, template))
      }
    }
    val writer = new StringWriter
    val pw = new PrintWriter(writer)
    val context = new DefaultRenderContext(uri, engine, pw)

    context.render(uri, model)
    assert("hello wille" == writer.toString)
    RenderEngine.reset
  }

  test("request Context"){
    val model = Map("greet" -> "hello")
    val engine = RenderEngine.getEngine
    val uri =  "requestContext.mustache"
    var greet = "some greet"

    engine.resourceLoader = new FileResourceLoader {
      override def resource(uri: String): Option[Resource] = {
        if(!uri.contains("bowler:/"))
          Some(Resource.fromText(uri, "Greet is: {{>bowler://com.recursivity.Component}}"))
        else {
          greet = BowlerRenderContext.contextModel("greet").asInstanceOf[String]
          Some(Resource.fromText(uri, greet))
        }
      }
    }
    val writer = new StringWriter
    val pw = new PrintWriter(writer)
    val context = new BowlerRenderContext(uri, engine, pw)

    context.render(uri, model)
    println(writer.toString)
    assert(writer.toString.equals("Greet is: hello"))
    //println("greet")
    assert(greet == "hello")
    RenderEngine.reset
  }

  test("dropdown model"){
    RenderEngine.reset
    val uri =  "/views/inline.ssp"
    val model = Map("bean" -> ScalateBean("hello"))
    val writer = new StringWriter
    val pw = new PrintWriter(writer)
    val context = new BowlerRenderContext(uri, RenderEngine.getEngine, pw)

    context.render(uri, model)
    println(writer.toString)

  }

}

case class ScalateBean(name: String)

object DropDown{
  def options(bean: ScalateBean): String = {
    "<option id=1>" + bean.name + "</option><option id=2>" + bean.name + "2</option>"

  }
}