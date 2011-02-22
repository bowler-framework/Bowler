package org.bowlerframework.view

/**
 * Container class to hold a model object for a view that will have a given alias/name in the overall View Model:<br/>
 * For instance, a property name for a JSON object where there are more than one model object in the view, OR<br/>
 * The Map key for a Scalate Map View Model
 */
case class ViewModel(alias: String, value: Any)