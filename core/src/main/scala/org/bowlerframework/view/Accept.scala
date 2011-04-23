package org.bowlerframework.view

/**
 * Created by IntelliJ IDEA.
 * User: wfaler
 * Date: 19/03/2011
 * Time: 00:19
 * To change this template use File | Settings | File Templates.
 */


sealed trait Accept

case object HTML extends Accept

case object JSON extends Accept

case object XML extends Accept