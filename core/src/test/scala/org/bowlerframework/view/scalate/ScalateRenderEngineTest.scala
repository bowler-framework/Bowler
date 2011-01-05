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
    println(writer.toString)
    assert(writer.toString.equals("<a href='http://www.google.com'>Google</a>"))
  }

}