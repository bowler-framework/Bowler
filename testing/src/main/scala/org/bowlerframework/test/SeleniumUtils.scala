package org.bowlerframework.test

import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.WebDriver

/**
* Created by IntelliJ IDEA.
 * User: wfaler
 * Date: 03/03/2011
 * Time: 23:23
 * To change this template use File | Settings | File Templates.
 */

object SeleniumUtils{

  var initializer : Function0[WebDriver] = () => new FirefoxDriver

  lazy val driver: WebDriver = initializer.apply

  def setDriver(init: () => WebDriver) = {
    initializer = {init}
  }

  def getDriver = driver

  def stop = driver.quit
}