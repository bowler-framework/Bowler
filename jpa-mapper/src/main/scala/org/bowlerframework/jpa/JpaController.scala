package org.bowlerframework.jpa

import org.bowlerframework.controller.InterceptingController
import org.bowlerframework.{Request, Response}
import com.recursivity.jpa.Jpa._

/**
 * Created by IntelliJ IDEA.
 * User: wfaler
 * Date: 30/01/2011
 * Time: 02:59
 * To change this template use File | Settings | File Templates.
 */

class JpaController extends InterceptingController {
  def around(request: Request, response: Response)(controller: (Request, Response) => Unit) = {
    transaction{controller(request, response)}
  }
}