package org.bowlerframework.view.scuery

import org.scalatest.FunSuite
import org.fusesource.scalate.scuery.Transformer
import xml.NodeSeq

/**
 * Created by IntelliJ IDEA.
 * User: wfaler
 * Date: 12/03/2011
 * Time: 01:04
 * To change this template use File | Settings | File Templates.
 */

class SimpleSqueryTest extends FunSuite {

  test("looping selector test") {
    val result = (new MyTransformer).apply(seq)
    assert("James" == ((result \ "table" \\ "tr")(0) \ "td")(0).text)
    assert("Mells" == ((result \ "table" \\ "tr")(0) \ "td")(1).text)

    assert("Hiram" == ((result \ "table" \\ "tr")(1) \ "td")(0).text)
    assert("Tampa" == ((result \ "table" \\ "tr")(1) \ "td")(1).text)
  }


  test("surround") {
    val page = new PageTransformer(new ChildTransformer(seq))
    val result = page.apply(around)
    assert("James" == ((result \ "body" \ "div" \ "table" \\ "tr")(0) \ "td")(0).text)

  }

  def seq = <div id="content">
    <table class="people">
      <tr>
        <th>Name</th>
        <th>Location</th>
      </tr>
      <tr class="person">
        <td class="name">DummyName</td>
        <td class="location">DummyLocation</td>
      </tr>
    </table>
  </div>

  def around = <html>
    <head>title</head>
    <body></body>
  </html>

}

case class Person(name: String, location: String)

class ChildTransformer(seq: NodeSeq) extends MyTransformer with MarkupContainer {
  override def render = super.apply(seq)
}

class PageTransformer(child: MarkupContainer) extends Transformer {
  $("body").contents = child.render
}

class MyTransformer extends Transformer {
  val people = List(Person("James", "Mells"),
    Person("Hiram", "Tampa"))

  $(".people").contents(
    node => {
      people.flatMap {
        p =>
          transform(node.$(".person")) {
            $ =>
              $(".name").contents = p.name
              $(".location").contents = p.location
          }
      }
    }
  )
}
