package org.bowlerframework.view.scuery

import org.scalatest.FunSuite
import stub.{ConcreteInheritanceCompositionComponent, ComposedPageComponent, SimpleTransformingComponent}

/**
 * Created by IntelliJ IDEA.
 * User: wfaler
 * Date: 14/03/2011
 * Time: 00:24
 * To change this template use File | Settings | File Templates.
 */

class TransformerComponentTest extends FunSuite {

  test("simple transformation") {
    val result = (new SimpleTransformingComponent).render
    assert("James" == ((result \ "table" \\ "tr")(0) \ "td")(0).text)
    assert("Mells" == ((result \ "table" \\ "tr")(0) \ "td")(1).text)

    assert("Hiram" == ((result \ "table" \\ "tr")(1) \ "td")(0).text)
    assert("Tampa" == ((result \ "table" \\ "tr")(1) \ "td")(1).text)
  }

  test("inheritance") {
    val page = new ConcreteInheritanceCompositionComponent
    val result = page.render
    assert("James" == ((result \ "body" \ "div" \ "table" \\ "tr")(0) \ "td")(0).text)
    assert("Mells" == ((result \ "body" \ "div" \ "table" \\ "tr")(0) \ "td")(1).text)

    assert("Hiram" == ((result \ "body" \ "div" \ "table" \\ "tr")(1) \ "td")(0).text)
    assert("Tampa" == ((result \ "body" \ "div" \ "table" \\ "tr")(1) \ "td")(1).text)
    assert("A Title" == (result \ "head" \ "title").text)
  }

  test("composition") {
    val page = new ComposedPageComponent(new SimpleTransformingComponent)
    val result = page.render
    assert("James" == ((result \ "body" \ "div" \ "table" \\ "tr")(0) \ "td")(0).text)
    assert("Mells" == ((result \ "body" \ "div" \ "table" \\ "tr")(0) \ "td")(1).text)

    assert("Hiram" == ((result \ "body" \ "div" \ "table" \\ "tr")(1) \ "td")(0).text)
    assert("Tampa" == ((result \ "body" \ "div" \ "table" \\ "tr")(1) \ "td")(1).text)
    assert("A Title" == (result \ "head" \ "title").text)
  }
}