package org.bowlerframework.view.scalate

import org.scalatest.FunSuite
import org.bowlerframework.jvm.DummyRequest
import java.io.IOException
import org.bowlerframework.{MappedPath, HTTP}
import selectors.{HeaderContainsSuffixSelector, HeaderContainsLayoutSelector, DefaultLayoutSelector}

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

 TemplateRegistry.appendSuffixSelectors(List(new HeaderContainsSuffixSelector("ipad", Map("User-Agent" -> "ipad")),
    new HeaderContainsSuffixSelector("iphone", Map("User-Agent" -> "iphone"))))

  test("get a template: root with no locale"){
    val template = resolver.resolveTemplate(makeRequest("/"), "/layouts/default")
    assert(template.template == "mustache")

  }

  test("get a template: root with locale"){
    val request = makeRequest("/")
    request.setLocales(List("es", "se"))
    val template = resolver.resolveTemplate(request, "/layouts/default")
    println(template)
    assert(template.template == "Svenska!")
  }


  test("previous stackoverflowexception bug"){
    val request = makeRequest("/")
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
    val request = makeRequest("/")
    val template = resolver.resolveLayout(request, Layout("default"))
    println(template)
    assert(template.template == "mustache")
  }

  test("layout with localisation"){
    val request = makeRequest("/")
    request.setLocales(List("es", "se"))
    val template = resolver.resolveLayout(request, Layout("default"))
    println(template)
    assert(template.template == "Svenska!")
  }

  test("view: / "){
    val request = makeRequest("/")
    request.setMappedPath(MappedPath("/", false))
    val template = resolver.resolveViewTemplate(request)
    assert(template.uri == "/views/GET/index.mustache")
    assert(template.template == "index.mustache")
  }

  test("/view: with localisation"){
    val request = makeRequest("/")
    request.setMappedPath(MappedPath("/", false))
    request.setLocales(List("es", "se"))
    val template = resolver.resolveViewTemplate(request)
    assert(template.uri == "/views/GET/index_se.ssp")
    assert(template.template == "svenska!")

  }


  test("sub folder index with ending /"){
    val request = makeRequest("/")
    request.setMappedPath(MappedPath("/widgets/", false))
    request.setLocales(List("es", "se"))
    val template = resolver.resolveViewTemplate(request)
    assert(template.uri == "/views/GET/widgets/index.ssp")
    assert(template.template == "widgets index")

  }

  test("sub folder index without / ending"){
    val request = makeRequest("/")
    request.setMappedPath(MappedPath("/widgets", false))
    request.setLocales(List("es", "se"))
    val template = resolver.resolveViewTemplate(request)
    assert(template.uri == "/views/GET/widgets/index.ssp")
    assert(template.template == "widgets index")

  }



  test("named parameter"){
    val request = makeRequest("/")
    request.setMappedPath(MappedPath("/widgets/:id", false))
    val template = resolver.resolveViewTemplate(request)
    assert(template.uri == "/views/GET/widgets/_id.ssp")
    assert(template.template == "this is the :id ssp")
  }

  test("nexted named parameter"){
    val request = makeRequest("/")
    request.setMappedPath(MappedPath("/widgets/:id", false))
    val template = resolver.resolveViewTemplate(request)
    assert(template.uri == "/views/GET/widgets/_id.ssp")
    assert(template.template == "this is the :id ssp")
  }

  test("view: / with suffix & localisation"){

    val request = makeRequest("/", Map("User-Agent" -> "ipad"))
    request.setLocales(List("es", "se"))
    request.setMappedPath(MappedPath("/", false))
    val template = resolver.resolveViewTemplate(request)
    assert(template.uri == "/views/GET/index_ipad_se.ssp")
    assert(template.template == "svenskt ipad index")

  }

  test("view: / subfolder with suffix"){
    val request = makeRequest("/widgets/", Map("User-Agent" -> "ipad"))
    request.setLocales(List("es", "se"))
    request.setMappedPath(MappedPath("/widgets", false))
    val template = resolver.resolveViewTemplate(request)
    assert(template.uri == "/views/GET/widgets/index_ipad.ssp")
    assert(template.template == "ipad widgets")
  }


  test("view: / with non-existent suffix"){
    val request = makeRequest("/widgets/", Map("User-Agent" -> "iphone"))
    request.setLocales(List("es", "se"))
    request.setMappedPath(MappedPath("/widgets", false))
    val template = resolver.resolveViewTemplate(request)
    assert(template.uri == "/views/GET/widgets/index.ssp")
    assert(template.template == "widgets index")
  }

  test("view: / with suffix but no localisation (conflicting lower level suffix with correct localisation)"){
    val request = makeRequest("/widgets/", Map("User-Agent" -> "iphone,ipad"))
    request.setLocales(List("es", "se"))
    request.setMappedPath(MappedPath("/widgets", false))
    val template = resolver.resolveViewTemplate(request)
    assert(template.uri == "/views/GET/widgets/index_ipad.ssp")
    assert(template.template == "ipad widgets")
  }

  /*

  test("wild cards"){

  }

  test("regex"){

}
   */




  def makeRequest(path: String) = new DummyRequest(HTTP.GET, path, Map(), null)
  def makeRequest(path: String, headers: Map[String, String]) = new DummyRequest(HTTP.GET, path, Map(), null, headers)
  //Accept-Language:
}