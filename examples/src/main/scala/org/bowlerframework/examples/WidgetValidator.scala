package org.bowlerframework.examples

import com.recursivity.commons.validator._
import org.bowlerframework.model.DefaultModelValidator

/**
 * Validates a widget - requires a .properties file on the classpath with the same package and class as this class.
 */

class WidgetValidator(widget: Widget) extends DefaultModelValidator(classOf[WidgetValidator]) {

  // adding individual field validators. The string key is the key used to lookup the property name for the error message.
  this.add(NotNullOrNone("name", {
    widget.name
  }))
  this.add(NotNullOrNone("id", {
    widget.id
  }))
  this.add(NotNullOrNone("yearMade", {
    widget.yearMade
  }))
  this.add(StringLength("name", 5, 50, {
    widget.name
  }))
  add(MaxInt("yearMade", 2100, {
    if (widget.yearMade != null)
      widget.yearMade.intValue
    else
      -500
  }))
  add(MinInt("yearMade", 1900, {
    if (widget.yearMade != null)
      widget.yearMade.intValue
    else
      -500
  }))
  add(MinLong("id", 1, {
    if (widget.id != null)
      widget.id.intValue
    else
      -500
  }))

}

/**
 * Checks uniqueness of a Widget for create
 */
class UniqueValidator(func: => Long) extends Validator {
  def getKey = "id"

  def getReplaceModel = List[Tuple2[String, Any]]()

  def isValid: Boolean = {
    if (Widgets.find(func) == None) return true
    else return false
  }
}