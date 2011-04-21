package org.bowlerframework.view.scalate

import org.scalatest.FunSuite
import util.matching.Regex
import org.bowlerframework.jvm.DummyRequest
import org.bowlerframework.{Request, POST, GET}
import org.bowlerframework.extractors.{Default, UriMatches, UriAndMethodMatches}
import collection.mutable.MutableList

/**
 * Created by IntelliJ IDEA.
 * User: wfaler
 * Date: 05/01/2011
 * Time: 22:12
 * To change this layout use File | Settings | File Templates.
 */

class TemplateRegistryTest extends FunSuite {
  val uriAndMethod = new UriAndMethodMatches[Layout](Layout("uriAndMethod"), POST, new Regex("^.*/hello/.*$"))
  val uriMatches = new UriMatches[Layout](Layout("uri"), new Regex("^.*/hello/.*$"))
  val default = new Default[Layout](Layout("default"))

  def resolver(request: Request): Option[Layout] = {
      var layout: Layout = null
      request match{
        case uriAndMethod(l) => Option(l)
        case uriMatches(l2) => Option(l2)
        case default(l3) => Option(l3)
        case _ => None
      }
  }

  TemplateRegistry.layoutResolver = resolver(_)


  val uriAndMethodSuffix = new UriAndMethodMatches[String]("uriAndMethod", POST, new Regex("^.*/hello/.*$"))
  val uriSuffix = new UriMatches[String]("uri", new Regex("^.*/hello/.*$"))

    def suffixResolver(request: Request): List[String] = {
    val list = new MutableList[String]
    request match{
      case uriAndMethodSuffix(ipad) => list += ipad
      case _ => {}
    }

    request match{
      case uriSuffix(iphone) => list += iphone
      case _ => {}
    }
    return list.toList
  }

  TemplateRegistry.suffixResolver = this.suffixResolver(_)

  test("get default layout") {
    assert("default" == TemplateRegistry.getLayout(new DummyRequest(GET, "/worldy/world", Map(), null)).get.name)
  }

  test("get URI specific layout") {
    assert("uri" == TemplateRegistry.getLayout(new DummyRequest(GET, "/hello/", Map(), null)).get.name)
  }

  test("get URI AND Method specific layout") {
    assert("uriAndMethod" == TemplateRegistry.getLayout(new DummyRequest(POST, "/hello/", Map(), null)).get.name)
  }

  test("get 2 suffixes") {
    assert(2 == TemplateRegistry.getSuffixes(new DummyRequest(POST, "/hello/", Map(), null)).size)
    assert(TemplateRegistry.getSuffixes(new DummyRequest(POST, "/hello/", Map(), null))(0) == "uriAndMethod")
    assert(TemplateRegistry.getSuffixes(new DummyRequest(POST, "/hello/", Map(), null))(1) == "uri")
  }

  test("get 1 suffixes") {
    assert(1 == TemplateRegistry.getSuffixes(new DummyRequest(GET, "/hello/", Map(), null)).size)
    assert(TemplateRegistry.getSuffixes(new DummyRequest(GET, "/hello/", Map(), null))(0) == "uri")
  }

  test("get no suffixes") {
    assert(0 == TemplateRegistry.getSuffixes(new DummyRequest(GET, "/worldy/world", Map(), null)).size)
  }

  test("regex string") {
    assert("^.*/hello/.*$" == new Regex("^.*/hello/.*$").toString)
  }

}