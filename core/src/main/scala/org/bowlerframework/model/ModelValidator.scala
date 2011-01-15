package org.bowlerframework.model

/**
 * Created by IntelliJ IDEA.
 * User: wfaler
 * Date: 15/01/2011
 * Time: 23:07
 * To change this template use File | Settings | File Templates.
 */

trait ModelValidator{
  def validate: Option[List[Tuple2[String, String]]]
}