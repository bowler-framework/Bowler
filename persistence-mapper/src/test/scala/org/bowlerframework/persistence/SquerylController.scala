package org.bowlerframework.persistence

import org.bowlerframework.controller.InterceptingController
import org.bowlerframework.{Request, Response}
import org.squeryl.SessionFactory
import org.squeryl.PrimitiveTypeMode._

/**
 * Created by IntelliJ IDEA.
 * User: wfaler
 * Date: 30/01/2011
 * Time: 02:59
 * To change this template use File | Settings | File Templates.
 */

class SquerylController extends InterceptingController {
  def around(request: Request, response: Response)(controller: (Request, Response) => Unit) = {
    val session = SessionFactory.newSession
    session.bindToCurrentThread
    try{
      transaction{
        controller(request, response)
      }
    }finally{
      session.close
    }
  }
}