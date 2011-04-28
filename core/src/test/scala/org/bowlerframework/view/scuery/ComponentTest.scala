package org.bowlerframework.view.scuery

import org.scalatest.FunSuite
import org.bowlerframework.jvm.DummyRequest
import stub.{LocalizedComponent, ExtendingComponent, MySimpleComponent}
import org.bowlerframework.{RequestResolver, POST}

/**
 * Created by IntelliJ IDEA.
 * User: wfaler
 * Date: 12/03/2011
 * Time: 18:56
 * To change this template use File | Settings | File Templates.
 */

class ComponentTest extends FunSuite {

  test("get classpath resource") {
    val result = (new MySimpleComponent).render
    assert("A Title" == (result \\ "title").text)
  }

  test("get inherited markup") {
    val result = (new ExtendingComponent).render
    assert("A Title" == (result \\ "title").text)
  }

  test("test localization") {
    val resolver = new DummyRequestResolver(List("es", "se"))
    MarkupContainer.requestResolver = resolver

    val result = (new LocalizedComponent).render
    assert("Svenska!" == (result \\ "body").text)
    resolver.newRequest(List[String]("es", "fi"))
    assert("English" == ((new LocalizedComponent).render \\ "body").text)
  }
}

class DummyRequestResolver(locales: List[String]) extends RequestResolver {
  var req = new DummyRequest(POST, "/", Map[String, String](), null)
  req.setLocales(locales)

  def request = this.req

  def newRequest(locales: List[String]) {
    req = new DummyRequest(POST, "/", Map[String, String](), null)
    req.setLocales(locales)
  }

}