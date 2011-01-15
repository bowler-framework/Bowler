package org.bowlerframework.examples

import com.recursivity.commons.validator._

/**
 * Created by IntelliJ IDEA.
 * User: wfaler
 * Date: 15/01/2011
 * Time: 01:25
 * To change this template use File | Settings | File Templates.
 */

class WidgetValidator(widget: Widget) extends ValidationGroup(new ClasspathMessageResolver(classOf[WidgetValidator])) {
  this.add(new NotNullOrNoneValidator("name", {
    widget.name
  }))
  this.add(new NotNullOrNoneValidator("id", {
    widget.id
  }))
  this.add(new NotNullOrNoneValidator("Year Made", {
    widget.yearMade
  }))
  this.add(new StringLengthValidator("name", 5, 50, {
    widget.name
  }))
  add(new MaxIntValidator("Year Made", 2100, {
    if (widget.yearMade != null)
      widget.yearMade.intValue
    else
      -500
  }))
  add(new MinIntValidator("Year MAde", 1900, {
    if (widget.yearMade != null)
      widget.yearMade.intValue
    else
      -500
  }))
  add(new MinLongValidator("Id", 1, {
    if (widget.id != null)
      widget.id.intValue
    else
      -500
  }))

  def validate: Option[List[Tuple2[String, String]]] = {
    val failures = validateAndReturnErrorMessages
    if (failures != null && failures.size > 0)
      Some(failures)
    else None
  }
}