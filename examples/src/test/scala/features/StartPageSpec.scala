package features

import org.scalatest.matchers.MustMatchers
import support.SeleniumUtils._
import org.scalatest.{FeatureSpec, GivenWhenThen}
import org.openqa.selenium.By

/**
 * Created by IntelliJ IDEA.
 * User: wfaler
 * Date: 10/03/2011
 * Time: 22:51
 * To change this template use File | Settings | File Templates.
 */

class StartPageSpec extends FeatureSpec with GivenWhenThen with MustMatchers {

  //evaluating { emptyStack.pop() } must produce [NoSuchElementException]

  feature("The user uses the homepage") {
    info("As a user")
    info("I want to be able to navigate from the startpage")
    info("So that I can navigate around the examples app")

    scenario("browser looks at startpage") {
      given("I am on the startpage")
      browser.get("http://localhost:8080")

      when("I look at the links")

      then("I should see 4 links")
      val ul = browser.findElement(By.id("links"))
      val listItems = ul.findElements(By.tagName("li"))
      listItems.size must be === 4

      and("the first link should be for widgets")
      val widgets = listItems.get(0).findElement(By.id("widgets"))
      widgets must not be null
      widgets.getText must be === "Check out our Widgets CRUD example with validation, JSON etc"
      widgets.getAttribute("href") must be === "/widgets"

      and("the second link should be a link to composable")
      val composable = listItems.get(1).findElement(By.id("composable"))
      composable must not be null
      composable.getText must be === "Widget listing, but with a composed Layout instead of default layout"
      composable.getAttribute("href") must be === "/composable/"

      and("the second link should be a link to JPA Example with cars")
      val cars = listItems.get(2).findElement(By.id("cars"))
      cars must not be null
      cars.getText must be === "CRUD example with JPA"
      cars.getAttribute("href") must be === "/cars/"

      and("the second link should be a link to Squeryl examples")
      val squeryl = listItems.get(3).findElement(By.id("people"))
      squeryl must not be null
      squeryl.getText must be === "CRUD example with Squeryl"
      squeryl.getAttribute("href") must be === "/people/"
    }
  }

}