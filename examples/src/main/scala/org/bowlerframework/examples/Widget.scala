package org.bowlerframework.examples

import collection.mutable.MutableList

/**
 * Just a simple bean/value object that gets "persisted" (well, not really) by the Widgets service
 */
case class Widget(id: Long, var name: String, var yearMade: java.lang.Integer, var description: String)

/**
 * Dumb CRUD service for Widget's
 */
object Widgets{

  var allWidgets = new MutableList[Widget]
  allWidgets += Widget(1, "AWidget", 2011, "hello world")

  def findAll: List[Widget] = allWidgets.toList

  def find(id: Long): Option[Widget] = {
    val w = allWidgets.find(w => (w.id == id))
    if(w != None){
      val widget = Widget(w.get.id, w.get.name, w.get.yearMade, w.get.description)
      return Some(widget)
    }else return w
  }

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