package features

import org.scalatest.matchers.MustMatchers
import support.SeleniumUtils._
import org.scalatest.{FeatureSpec, GivenWhenThen}
import org.openqa.selenium.By

/**
 * Created by IntelliJ IDEA.
 * User: wfaler
 * Date: 10/03/2011
 * Time: 23:37
 * To change this template use File | Settings | File Templates.
 */

class WidgetSpec extends FeatureSpec with GivenWhenThen with MustMatchers {

    feature("The user uses the homepage") {
      info("As a user")
      info("I want to be able to navigate from the startpage")
      info("So that I can navigate around the examples app")

      scenario("The user goes to the Widgets page for the first time"){
        given("I am on the startpage")
        browser.get("http://localhost:8080")

        when("I click the 'widgets' link")
        val widgetsLink = browser.findElement(By.id("widgets"))
        widgetsLink.click

        then("I should be on the widgets page")
        browser.getCurrentUrl must be === "http://localhost:8080/widgets"

        and("I should see a table")
        val tableBody = browser.findElement(By.tagName("tbody"))
        tableBody must not be null

        and("The table should have one pre-existing Widget entry")
        val tr = tableBody.findElements(By.tagName("tr"))
        tr.size must be === 1
      }
    }

  feature("The user creates/updates/deletes widgets"){
    info("As a user")
    info("I want to be able to create, update and delete widgets")
    info("So that I can have only the widgets I want")
    pending

  }



}