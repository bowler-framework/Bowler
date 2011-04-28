package org.bowlerframework.view.scuery.stub

import org.bowlerframework.view.scuery.{Person, Component}

/**
 * Created by IntelliJ IDEA.
 * User: wfaler
 * Date: 14/03/2011
 * Time: 00:22
 * To change this template use File | Settings | File Templates.
 */

class SimpleTransformingComponent extends Component {

  //massive repeating list to test performance
  val people = List(Person("James", "Mells"),
    Person("Hiram", "Tampa"), Person("Hiram", "Tampa"), Person("Hiram", "Tampa"), Person("Hiram", "Tampa"),
    Person("Hiram", "Tampa"), Person("Hiram", "Tampa"), Person("Hiram", "Tampa"), Person("Hiram", "Tampa"),
    Person("Hiram", "Tampa"), Person("Hiram", "Tampa"), Person("Hiram", "Tampa"), Person("Hiram", "Tampa"),
    Person("Hiram", "Tampa"), Person("Hiram", "Tampa"), Person("Hiram", "Tampa"), Person("Hiram", "Tampa"),
    Person("Hiram", "Tampa"), Person("Hiram", "Tampa"), Person("Hiram", "Tampa"), Person("Hiram", "Tampa"),
    Person("Hiram", "Tampa"), Person("Hiram", "Tampa"), Person("Hiram", "Tampa"), Person("Hiram", "Tampa"),
    Person("Hiram", "Tampa"), Person("Hiram", "Tampa"), Person("Hiram", "Tampa"), Person("Hiram", "Tampa"),
    Person("Hiram", "Tampa"), Person("Hiram", "Tampa"), Person("Hiram", "Tampa"), Person("Hiram", "Tampa"),
    Person("Hiram", "Tampa"), Person("Hiram", "Tampa"), Person("Hiram", "Tampa"), Person("Hiram", "Tampa"),
    Person("Hiram", "Tampa"), Person("Hiram", "Tampa"), Person("Hiram", "Tampa"), Person("Hiram", "Tampa"),
    Person("Hiram", "Tampa"), Person("Hiram", "Tampa"), Person("Hiram", "Tampa"), Person("Hiram", "Tampa"))

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