package org.bowlerframework.examples

import collection.mutable.MutableList

/**
 * Created by IntelliJ IDEA.
 * User: wfaler
 * Date: 14/01/2011
 * Time: 18:07
 * To change this template use File | Settings | File Templates.
 */
case class Widget(id: Int, var name: String, var yearMade: Int, var description: String)

object Widgets{

  var allWidgets = new MutableList[Widget]
  allWidgets += Widget(1, "wille", 1978, "hello world")

  def findAll: List[Widget] = allWidgets.toList

  def find(id: Int): Option[Widget] = allWidgets.find(w => (w.id == id))

  def delete(widget: Widget) ={
    allWidgets = allWidgets.filter(p => (p.id != widget.id))
  }
  def create(widget: Widget) ={
    if(find(widget.id) != None)
      throw new IllegalArgumentException("Widget already exists!")
    allWidgets += widget
  }

  def update(widget: Widget) = {
    delete(widget)
    create(widget)
    Some(widget)
  }
}