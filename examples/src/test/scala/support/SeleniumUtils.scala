package support

import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.firefox.FirefoxDriver

object SeleniumUtils{
	var initializer: Function0[WebDriver] = () => {new FirefoxDriver}
	var initialized = false
	lazy val driver: WebDriver = {
		initialized = true
		initializer.apply		
	}
	
	def setInitializer(init: () => WebDriver) = {initializer = {init}}
	
	def browser = driver
	
	def quit = {
		if(initialized) driver.quit
	}
}