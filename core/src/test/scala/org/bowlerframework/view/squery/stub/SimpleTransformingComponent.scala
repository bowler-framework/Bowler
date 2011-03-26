package org.bowlerframework.view.squery.stub

import org.bowlerframework.view.squery.{Person, TransformerComponent}

/**
 * Created by IntelliJ IDEA.
 * User: wfaler
 * Date: 14/03/2011
 * Time: 00:22
 * To change this template use File | Settings | File Templates.
 */

class SimpleTransformingComponent extends TransformerComponent {
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