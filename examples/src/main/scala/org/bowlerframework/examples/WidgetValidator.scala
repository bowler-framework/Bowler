package org.bowlerframework.examples

import com.recursivity.commons.validator._
import org.bowlerframework.model.DefaultModelValidator

/**
 * Created by IntelliJ IDEA.
 * User: wfaler
 * Date: 15/01/2011
 * Time: 01:25
 * To change this template use File | Settings | File Templates.
 */

class WidgetValidator(widget: Widget) extends DefaultModelValidator(classOf[WidgetValidator]) {
  this.add(new NotNullOrNoneValidator("name", {
    widget.name
  }))
  this.add(new NotNullOrNoneValidator("id", {
    widget.id
  }))
  this.add(new NotNullOrNoneValidator("yearMade", {
    widget.yearMade
  }))
  this.add(new StringLengthValidator("name", 5, 50, {
    widget.name
  }))
  add(new MaxIntValidator("yearMade", 2100, {
    if (widget.yearMade != null)
      widget.yearMade.intValue
    else
      -500
  }))
  add(new MinIntValidator("yearMade", 1900, {
    if (widget.yearMade != null)
      widget.yearMade.intValue
    else
      -500
  }))
  add(new MinLongValidator("id", 1, {
    if (widget.id != null)
      widget.id.intValue
    else
      -500
  }))

}

class UniqueValidator(func: => Long) extends Validator {
  def getKey = "id"

  def getReplaceModel = List[Tuple2[String, Any]]()

  def isValid: Boolean = {
    if (Widgets.find(func) == None) return true
    else return false
  }
}