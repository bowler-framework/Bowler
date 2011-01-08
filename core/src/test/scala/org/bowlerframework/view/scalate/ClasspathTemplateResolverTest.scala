package org.bowlerframework.view.scalate

import org.scalatest.FunSuite
import selectors.{HeaderContainsLayoutSelector, DefaultLayoutSelector}
import org.bowlerframework.HTTP
import org.bowlerframework.jvm.DummyRequest
import java.io.IOException

/**
 * Created by IntelliJ IDEA.
 * User: wfaler
 * Date: 07/01/2011
 * Time: 23:43
 * To change this template use File | Settings | File Templates.
 */

class ClasspathTemplateResolverTest extends FunSuite{
  TemplateRegistry.reset

  val resolver = TemplateRegistry.templateResolver

 // TemplateRegistry.appendSuffixSelectors(List(new UriAndMethodSuffixSelector("uriAndMethod", HTTP.POST, new Regex("^.*/hello/.*$")),
 //   new UriSuffixSelector("uri", new Regex("^.*/hello/.*$"))))

  test("get a template: root with no locale"){
    val template = resolver.resolveTemplate(makeRequest, "/layouts/default")
    assert(template.template == "mustache")

  }

  test("get a template: root with locale"){
    val request = makeRequest
    request.setLocales(List("es", "se"))
    val template = resolver.resolveTemplate(request, "/layouts/default")
    println(template)
    assert(template.template == "Svenska!")
  }


  test("previous stackoverflowexception bug"){
    val request = makeRequest
    try{
      val template = resolver.resolveLayout(request, Layout("overflow-baby"))
    }catch{
      case e: IOException => {
        val message = "Could not find a template of type .mustache, .ssp, .jade or .scaml with path: classpath:///layouts/overflow-baby"
        assert(e.getMessage == message)
      }
    }

  }

  test("layout without localisation"){
    val request = makeRequest
    val template = resolver.resolveLayout(request, Layout("default"))
    println(template)
    assert(template.template == "mustache")
  }

  test("layout with localisation"){
    val request = makeRequest
    request.setLocales(List("es", "se"))
    val template = resolver.resolveLayout(request, Layout("default"))
    println(template)
    assert(template.template == "Svenska!")
  }



  /*test("sub folder index"){

  }

  test("wild cards"){

  }

  test("named parameter"){

  }

  test("nexted named parameter"){

  } */


  def makeRequest = new DummyRequest(HTTP.GET, "/hello/", Map(), null)
  def makeRequest(headers: Map[String, String]) = new DummyRequest(HTTP.GET, "/hello/", Map(), null, headers)
  //Accept-Language:
}