package org.bowlerframework

import javax.servlet.http.{HttpServletRequest, HttpServletResponse, HttpSession}
import org.scalatra.util.{MultiMapHeadView, MapWithIndifferentAccess}
import org.apache.commons.fileupload.FileItem
import collection.mutable.HashMap

/**
 * Created by IntelliJ IDEA.
 * User: wfaler
 * Date: Dec 10, 2010
 * Time: 1:47:28 AM
 * To change this template use File | Settings | File Templates.
 */

case class RequestScope(request: HttpServletRequest, response: HttpServletResponse, session: Session, params: Map[String, Any]){
  
}