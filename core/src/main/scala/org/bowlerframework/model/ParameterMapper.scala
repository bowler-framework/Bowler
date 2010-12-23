package org.bowlerframework.model


import org.bowlerframework.{BowlerConfigurator, Request}

/**
 * Created by IntelliJ IDEA.
 * User: wfaler
 * Date: 12/12/2010
 * Time: 23:42
 * To change this template use File | Settings | File Templates.
 */

trait ParameterMapper {
  def mapRequest[T](request: Request, nameHint: String = null)(func: T => Any)(implicit m: Manifest[T]): Any = {
    val param = BowlerConfigurator.getRequestMapper(request).getValue[T](request, nameHint)
    try {
      func(param)
    } catch {
      case e: ClassCastException => throw new RequestMapperException("Could not map parameter to a value! If a " +
        "parameter is not mandatory, you should consider using Option[" + m.toString + "]!")
    }
  }
}