import cuke4duke.{EN, ScalaDsl}
import org.scalatest.matchers.ShouldMatchers
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.firefox.FirefoxDriver
 
class CucumberSteps extends ScalaDsl with EN with ShouldMatchers {
 
  private var givenCalled = false
  private var whenCalled = false
  private var count: Int = 0
  private var greet: String = null
 
  Given("""^An SBT project$""") {
    givenCalled = true
  }

  Given("""^I have (\d+) cucumbers in my hands$""") { cukes:Int =>
    count = cukes * 2
  }

  Given("""^I have entered (.*) into the console$""") { cukes:String =>
    greet = cukes
  }
 
  When("""^I run the cucumber goal$""") {
    whenCalled = true
  }
 
  Then("""^Cucumber is executed against my features and step definitions$""") {
    givenCalled should be (true)
    whenCalled should be (true)
	count should be (8)
	greet should be ("hello")
	
	val driver = new FirefoxDriver
	driver.get("http://www.google.com")
 	val element = driver.findElement(By.name("q"))
	element.sendKeys("Cheese!")
	element.submit
	println("Page title is: " + driver.getTitle())
	driver.quit
  }
}