package org.bowlerframework.model


import org.bowlerframework.{BowlerConfigurator, Request}

/**
 * Maps from a Request into any number of object types (limited to 12) IF a mapping can be resolved.
 *
 * To give the mapper a hint, you may prefix request parameters with the alias of the object type you want them mapped into,
 * for instance: myBean.id for the id of an object of type MyBean (if no other alias has been registered).
 *
 */

trait ParameterMapper {
  def mapRequest[T](request: Request, nameHint: String = null)(func: T => Any)(implicit m: Manifest[T]): Any = {
    val param = BowlerConfigurator.getRequestMapper(request).getValue[T](request, nameHint)
    ParameterMapperHelper.handleErrors{func(param)}
  }

  def mapRequest[T1, T2](request: Request, nameHints: List[String])(func: (T1, T2) => Any)(implicit m: Manifest[T1], m2: Manifest[T2]): Any = {
    validateNamehints(2, nameHints)
    if(nameHints == null || nameHints.size == 0){
      val p1 = BowlerConfigurator.getRequestMapper(request).getValue[T1](request, null)
      val p2 = BowlerConfigurator.getRequestMapper(request).getValue[T2](request, null)
      ParameterMapperHelper.handleErrors{func(p1,p2)}
    }else{
      val p1 = BowlerConfigurator.getRequestMapper(request).getValue[T1](request, nameHints(0))
      val p2 = BowlerConfigurator.getRequestMapper(request).getValue[T2](request, nameHints(1))
      ParameterMapperHelper.handleErrors{func(p1,p2)}
    }
  }

  def mapRequest[T1, T2, T3](request: Request, nameHints: List[String])(func: (T1, T2, T3) => Any)(implicit m: Manifest[T1], m2: Manifest[T2], m3: Manifest[T3]): Any = {
    validateNamehints(3, nameHints)
    if(nameHints == null || nameHints.size == 0){
      val p1 = BowlerConfigurator.getRequestMapper(request).getValue[T1](request, null)
      val p2 = BowlerConfigurator.getRequestMapper(request).getValue[T2](request, null)
      val p3 = BowlerConfigurator.getRequestMapper(request).getValue[T3](request, null)
      ParameterMapperHelper.handleErrors{func(p1,p2, p3)}
    }else{
      val p1 = BowlerConfigurator.getRequestMapper(request).getValue[T1](request, nameHints(0))
      val p2 = BowlerConfigurator.getRequestMapper(request).getValue[T2](request, nameHints(1))
      val p3 = BowlerConfigurator.getRequestMapper(request).getValue[T3](request, nameHints(2))
      ParameterMapperHelper.handleErrors{func(p1,p2, p3)}
    }
  }


  def mapRequest[T1, T2, T3, T4](request: Request, nameHints: List[String])(func: (T1, T2, T3, T4) => Any)(implicit m: Manifest[T1], m2: Manifest[T2], m3: Manifest[T3], m4: Manifest[T4]): Any = {
    validateNamehints(4, nameHints)
    if(nameHints == null || nameHints.size == 0){
      val p1 = BowlerConfigurator.getRequestMapper(request).getValue[T1](request, null)
      val p2 = BowlerConfigurator.getRequestMapper(request).getValue[T2](request, null)
      val p3 = BowlerConfigurator.getRequestMapper(request).getValue[T3](request, null)
      val p4 = BowlerConfigurator.getRequestMapper(request).getValue[T4](request, null)
      ParameterMapperHelper.handleErrors{func(p1,p2, p3, p4)}
    }else{
      val p1 = BowlerConfigurator.getRequestMapper(request).getValue[T1](request, nameHints(0))
      val p2 = BowlerConfigurator.getRequestMapper(request).getValue[T2](request, nameHints(1))
      val p3 = BowlerConfigurator.getRequestMapper(request).getValue[T3](request, nameHints(2))
      val p4 = BowlerConfigurator.getRequestMapper(request).getValue[T4](request, nameHints(3))
      ParameterMapperHelper.handleErrors{func(p1,p2, p3, p4)}
    }
  }

  def mapRequest[T1, T2, T3, T4, T5](request: Request, nameHints: List[String])(func: (T1, T2, T3, T4, T5) => Any)
                                    (implicit m: Manifest[T1], m2: Manifest[T2], m3: Manifest[T3], m4: Manifest[T4], m5: Manifest[T5]): Any = {
    validateNamehints(5, nameHints)
    if(nameHints == null || nameHints.size == 0){
      val p1 = BowlerConfigurator.getRequestMapper(request).getValue[T1](request, null)
      val p2 = BowlerConfigurator.getRequestMapper(request).getValue[T2](request, null)
      val p3 = BowlerConfigurator.getRequestMapper(request).getValue[T3](request, null)
      val p4 = BowlerConfigurator.getRequestMapper(request).getValue[T4](request, null)
      val p5 = BowlerConfigurator.getRequestMapper(request).getValue[T5](request, null)
      ParameterMapperHelper.handleErrors{func(p1,p2, p3, p4, p5)}
    }else{
      val p1 = BowlerConfigurator.getRequestMapper(request).getValue[T1](request, nameHints(0))
      val p2 = BowlerConfigurator.getRequestMapper(request).getValue[T2](request, nameHints(1))
      val p3 = BowlerConfigurator.getRequestMapper(request).getValue[T3](request, nameHints(2))
      val p4 = BowlerConfigurator.getRequestMapper(request).getValue[T4](request, nameHints(3))
      val p5 = BowlerConfigurator.getRequestMapper(request).getValue[T5](request, nameHints(4))
      ParameterMapperHelper.handleErrors{func(p1,p2, p3, p4, p5)}
    }
  }

  def mapRequest[T1, T2, T3, T4, T5, T6](request: Request, nameHints: List[String])(func: (T1, T2, T3, T4, T5, T6) => Any)
                                        (implicit m: Manifest[T1], m2: Manifest[T2], m3: Manifest[T3],m4: Manifest[T4],m5: Manifest[T5],m6: Manifest[T6]): Any = {
    validateNamehints(6, nameHints)
    if(nameHints == null || nameHints.size == 0){
      val p1 = BowlerConfigurator.getRequestMapper(request).getValue[T1](request, null)
      val p2 = BowlerConfigurator.getRequestMapper(request).getValue[T2](request, null)
      val p3 = BowlerConfigurator.getRequestMapper(request).getValue[T3](request, null)
      val p4 = BowlerConfigurator.getRequestMapper(request).getValue[T4](request, null)
      val p5 = BowlerConfigurator.getRequestMapper(request).getValue[T5](request, null)
      val p6 = BowlerConfigurator.getRequestMapper(request).getValue[T6](request, null)
      ParameterMapperHelper.handleErrors{func(p1,p2, p3, p4, p5, p6)}
    }else{
      val p1 = BowlerConfigurator.getRequestMapper(request).getValue[T1](request, nameHints(0))
      val p2 = BowlerConfigurator.getRequestMapper(request).getValue[T2](request, nameHints(1))
      val p3 = BowlerConfigurator.getRequestMapper(request).getValue[T3](request, nameHints(2))
      val p4 = BowlerConfigurator.getRequestMapper(request).getValue[T4](request, nameHints(3))
      val p5 = BowlerConfigurator.getRequestMapper(request).getValue[T5](request, nameHints(4))
      val p6 = BowlerConfigurator.getRequestMapper(request).getValue[T6](request, nameHints(5))
      ParameterMapperHelper.handleErrors{func(p1,p2, p3, p4, p5, p6)}
    }
  }

  def mapRequest[T1, T2, T3, T4, T5, T6, T7](request: Request, nameHints: List[String])(func: (T1, T2, T3,T4,T5,T6,T7) => Any)
                                            (implicit m: Manifest[T1], m2: Manifest[T2], m3: Manifest[T3],m4: Manifest[T4],m5: Manifest[T5],m6: Manifest[T6],m7: Manifest[T7]): Any = {
    validateNamehints(7, nameHints)
    if(nameHints == null || nameHints.size == 0){
      val p1 = BowlerConfigurator.getRequestMapper(request).getValue[T1](request, null)
      val p2 = BowlerConfigurator.getRequestMapper(request).getValue[T2](request, null)
      val p3 = BowlerConfigurator.getRequestMapper(request).getValue[T3](request, null)
      val p4 = BowlerConfigurator.getRequestMapper(request).getValue[T4](request, null)
      val p5 = BowlerConfigurator.getRequestMapper(request).getValue[T5](request, null)
      val p6 = BowlerConfigurator.getRequestMapper(request).getValue[T6](request, null)
      val p7 = BowlerConfigurator.getRequestMapper(request).getValue[T7](request, null)
      ParameterMapperHelper.handleErrors{func(p1,p2, p3,p4,p5,p6,p7)}
    }else{
      val p1 = BowlerConfigurator.getRequestMapper(request).getValue[T1](request, nameHints(0))
      val p2 = BowlerConfigurator.getRequestMapper(request).getValue[T2](request, nameHints(1))
      val p3 = BowlerConfigurator.getRequestMapper(request).getValue[T3](request, nameHints(2))
      val p4 = BowlerConfigurator.getRequestMapper(request).getValue[T4](request, nameHints(3))
      val p5 = BowlerConfigurator.getRequestMapper(request).getValue[T5](request, nameHints(4))
      val p6 = BowlerConfigurator.getRequestMapper(request).getValue[T6](request, nameHints(5))
      val p7 = BowlerConfigurator.getRequestMapper(request).getValue[T7](request, nameHints(6))
      ParameterMapperHelper.handleErrors{func(p1,p2, p3,p4,p5,p6,p7)}
    }
  }

  def mapRequest[T1, T2, T3, T4, T5, T6, T7, T8](request: Request, nameHints: List[String])(func: (T1, T2, T3,T4,T5,T6,T7, T8) => Any)
                                            (implicit m: Manifest[T1], m2: Manifest[T2], m3: Manifest[T3],m4: Manifest[T4],m5: Manifest[T5],m6: Manifest[T6],m7: Manifest[T7], m8: Manifest[T8]): Any = {
    validateNamehints(8, nameHints)
    if(nameHints == null || nameHints.size == 0){
      val p1 = BowlerConfigurator.getRequestMapper(request).getValue[T1](request, null)
      val p2 = BowlerConfigurator.getRequestMapper(request).getValue[T2](request, null)
      val p3 = BowlerConfigurator.getRequestMapper(request).getValue[T3](request, null)
      val p4 = BowlerConfigurator.getRequestMapper(request).getValue[T4](request, null)
      val p5 = BowlerConfigurator.getRequestMapper(request).getValue[T5](request, null)
      val p6 = BowlerConfigurator.getRequestMapper(request).getValue[T6](request, null)
      val p7 = BowlerConfigurator.getRequestMapper(request).getValue[T7](request, null)
      val p8 = BowlerConfigurator.getRequestMapper(request).getValue[T8](request, null)
      ParameterMapperHelper.handleErrors{func(p1,p2, p3,p4,p5,p6,p7, p8)}
    }else{
      val p1 = BowlerConfigurator.getRequestMapper(request).getValue[T1](request, nameHints(0))
      val p2 = BowlerConfigurator.getRequestMapper(request).getValue[T2](request, nameHints(1))
      val p3 = BowlerConfigurator.getRequestMapper(request).getValue[T3](request, nameHints(2))
      val p4 = BowlerConfigurator.getRequestMapper(request).getValue[T4](request, nameHints(3))
      val p5 = BowlerConfigurator.getRequestMapper(request).getValue[T5](request, nameHints(4))
      val p6 = BowlerConfigurator.getRequestMapper(request).getValue[T6](request, nameHints(5))
      val p7 = BowlerConfigurator.getRequestMapper(request).getValue[T7](request, nameHints(6))
      val p8 = BowlerConfigurator.getRequestMapper(request).getValue[T8](request, nameHints(7))
      ParameterMapperHelper.handleErrors{func(p1,p2, p3,p4,p5,p6,p7,p8)}
    }
  }

  def mapRequest[T1, T2, T3, T4, T5, T6, T7, T8, T9](request: Request, nameHints: List[String])(func: (T1, T2, T3,T4,T5,T6,T7, T8, T9) => Any)
                                            (implicit m: Manifest[T1], m2: Manifest[T2], m3: Manifest[T3],m4: Manifest[T4],m5: Manifest[T5],m6: Manifest[T6],m7: Manifest[T7],
                                             m8: Manifest[T8],m9: Manifest[T9]): Any = {
    validateNamehints(9, nameHints)
    if(nameHints == null || nameHints.size == 0){
      val p1 = BowlerConfigurator.getRequestMapper(request).getValue[T1](request, null)
      val p2 = BowlerConfigurator.getRequestMapper(request).getValue[T2](request, null)
      val p3 = BowlerConfigurator.getRequestMapper(request).getValue[T3](request, null)
      val p4 = BowlerConfigurator.getRequestMapper(request).getValue[T4](request, null)
      val p5 = BowlerConfigurator.getRequestMapper(request).getValue[T5](request, null)
      val p6 = BowlerConfigurator.getRequestMapper(request).getValue[T6](request, null)
      val p7 = BowlerConfigurator.getRequestMapper(request).getValue[T7](request, null)
      val p8 = BowlerConfigurator.getRequestMapper(request).getValue[T8](request, null)
      val p9 = BowlerConfigurator.getRequestMapper(request).getValue[T9](request, null)
      ParameterMapperHelper.handleErrors{func(p1,p2, p3,p4,p5,p6,p7, p8, p9)}
    }else{
      val p1 = BowlerConfigurator.getRequestMapper(request).getValue[T1](request, nameHints(0))
      val p2 = BowlerConfigurator.getRequestMapper(request).getValue[T2](request, nameHints(1))
      val p3 = BowlerConfigurator.getRequestMapper(request).getValue[T3](request, nameHints(2))
      val p4 = BowlerConfigurator.getRequestMapper(request).getValue[T4](request, nameHints(3))
      val p5 = BowlerConfigurator.getRequestMapper(request).getValue[T5](request, nameHints(4))
      val p6 = BowlerConfigurator.getRequestMapper(request).getValue[T6](request, nameHints(5))
      val p7 = BowlerConfigurator.getRequestMapper(request).getValue[T7](request, nameHints(6))
      val p8 = BowlerConfigurator.getRequestMapper(request).getValue[T8](request, nameHints(7))
      val p9 = BowlerConfigurator.getRequestMapper(request).getValue[T9](request, nameHints(8))
      ParameterMapperHelper.handleErrors{func(p1,p2, p3,p4,p5,p6,p7,p8,p9)}
    }
  }

  def mapRequest[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10](request: Request, nameHints: List[String])(func: (T1, T2, T3,T4,T5,T6,T7, T8, T9, T10) => Any)
                                            (implicit m: Manifest[T1], m2: Manifest[T2], m3: Manifest[T3],m4: Manifest[T4],m5: Manifest[T5],m6: Manifest[T6],m7: Manifest[T7],
                                             m8: Manifest[T8],m9: Manifest[T9],m10: Manifest[T10]): Any = {
    validateNamehints(10, nameHints)
    if(nameHints == null || nameHints.size == 0){
      val p1 = BowlerConfigurator.getRequestMapper(request).getValue[T1](request, null)
      val p2 = BowlerConfigurator.getRequestMapper(request).getValue[T2](request, null)
      val p3 = BowlerConfigurator.getRequestMapper(request).getValue[T3](request, null)
      val p4 = BowlerConfigurator.getRequestMapper(request).getValue[T4](request, null)
      val p5 = BowlerConfigurator.getRequestMapper(request).getValue[T5](request, null)
      val p6 = BowlerConfigurator.getRequestMapper(request).getValue[T6](request, null)
      val p7 = BowlerConfigurator.getRequestMapper(request).getValue[T7](request, null)
      val p8 = BowlerConfigurator.getRequestMapper(request).getValue[T8](request, null)
      val p9 = BowlerConfigurator.getRequestMapper(request).getValue[T9](request, null)
      val p10 = BowlerConfigurator.getRequestMapper(request).getValue[T10](request, null)
      ParameterMapperHelper.handleErrors{func(p1,p2, p3,p4,p5,p6,p7, p8, p9,p10)}
    }else{
      val p1 = BowlerConfigurator.getRequestMapper(request).getValue[T1](request, nameHints(0))
      val p2 = BowlerConfigurator.getRequestMapper(request).getValue[T2](request, nameHints(1))
      val p3 = BowlerConfigurator.getRequestMapper(request).getValue[T3](request, nameHints(2))
      val p4 = BowlerConfigurator.getRequestMapper(request).getValue[T4](request, nameHints(3))
      val p5 = BowlerConfigurator.getRequestMapper(request).getValue[T5](request, nameHints(4))
      val p6 = BowlerConfigurator.getRequestMapper(request).getValue[T6](request, nameHints(5))
      val p7 = BowlerConfigurator.getRequestMapper(request).getValue[T7](request, nameHints(6))
      val p8 = BowlerConfigurator.getRequestMapper(request).getValue[T8](request, nameHints(7))
      val p9 = BowlerConfigurator.getRequestMapper(request).getValue[T9](request, nameHints(8))
      val p10 = BowlerConfigurator.getRequestMapper(request).getValue[T10](request, nameHints(9))
      ParameterMapperHelper.handleErrors{func(p1,p2, p3,p4,p5,p6,p7,p8,p9,p10)}
    }
  }

  def mapRequest[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11](request: Request, nameHints: List[String])(func: (T1, T2, T3,T4,T5,T6,T7, T8, T9, T10, T11) => Any)
                                            (implicit m: Manifest[T1], m2: Manifest[T2], m3: Manifest[T3],m4: Manifest[T4],m5: Manifest[T5],m6: Manifest[T6],m7: Manifest[T7],
                                             m8: Manifest[T8],m9: Manifest[T9],m10: Manifest[T10],m11: Manifest[T11]): Any = {
    validateNamehints(11, nameHints)
    if(nameHints == null || nameHints.size == 0){
      val p1 = BowlerConfigurator.getRequestMapper(request).getValue[T1](request, null)
      val p2 = BowlerConfigurator.getRequestMapper(request).getValue[T2](request, null)
      val p3 = BowlerConfigurator.getRequestMapper(request).getValue[T3](request, null)
      val p4 = BowlerConfigurator.getRequestMapper(request).getValue[T4](request, null)
      val p5 = BowlerConfigurator.getRequestMapper(request).getValue[T5](request, null)
      val p6 = BowlerConfigurator.getRequestMapper(request).getValue[T6](request, null)
      val p7 = BowlerConfigurator.getRequestMapper(request).getValue[T7](request, null)
      val p8 = BowlerConfigurator.getRequestMapper(request).getValue[T8](request, null)
      val p9 = BowlerConfigurator.getRequestMapper(request).getValue[T9](request, null)
      val p10 = BowlerConfigurator.getRequestMapper(request).getValue[T10](request, null)
      val p11 = BowlerConfigurator.getRequestMapper(request).getValue[T11](request, null)
      ParameterMapperHelper.handleErrors{func(p1,p2, p3,p4,p5,p6,p7, p8, p9,p10,p11)}
    }else{
      val p1 = BowlerConfigurator.getRequestMapper(request).getValue[T1](request, nameHints(0))
      val p2 = BowlerConfigurator.getRequestMapper(request).getValue[T2](request, nameHints(1))
      val p3 = BowlerConfigurator.getRequestMapper(request).getValue[T3](request, nameHints(2))
      val p4 = BowlerConfigurator.getRequestMapper(request).getValue[T4](request, nameHints(3))
      val p5 = BowlerConfigurator.getRequestMapper(request).getValue[T5](request, nameHints(4))
      val p6 = BowlerConfigurator.getRequestMapper(request).getValue[T6](request, nameHints(5))
      val p7 = BowlerConfigurator.getRequestMapper(request).getValue[T7](request, nameHints(6))
      val p8 = BowlerConfigurator.getRequestMapper(request).getValue[T8](request, nameHints(7))
      val p9 = BowlerConfigurator.getRequestMapper(request).getValue[T9](request, nameHints(8))
      val p10 = BowlerConfigurator.getRequestMapper(request).getValue[T10](request, nameHints(9))
      val p11 = BowlerConfigurator.getRequestMapper(request).getValue[T11](request, nameHints(10))
      ParameterMapperHelper.handleErrors{func(p1,p2, p3,p4,p5,p6,p7,p8,p9,p10,p11)}
    }
  }


  def mapRequest[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12](request: Request, nameHints: List[String])(func: (T1, T2, T3,T4,T5,T6,T7, T8, T9, T10,T11,T12) => Any)
                                            (implicit m: Manifest[T1], m2: Manifest[T2], m3: Manifest[T3],m4: Manifest[T4],m5: Manifest[T5],m6: Manifest[T6],m7: Manifest[T7],
                                             m8: Manifest[T8],m9: Manifest[T9],m10: Manifest[T10],m11: Manifest[T11],m12: Manifest[T12]): Any = {
    validateNamehints(12, nameHints)
    if(nameHints == null || nameHints.size == 0){
      val p1 = BowlerConfigurator.getRequestMapper(request).getValue[T1](request, null)
      val p2 = BowlerConfigurator.getRequestMapper(request).getValue[T2](request, null)
      val p3 = BowlerConfigurator.getRequestMapper(request).getValue[T3](request, null)
      val p4 = BowlerConfigurator.getRequestMapper(request).getValue[T4](request, null)
      val p5 = BowlerConfigurator.getRequestMapper(request).getValue[T5](request, null)
      val p6 = BowlerConfigurator.getRequestMapper(request).getValue[T6](request, null)
      val p7 = BowlerConfigurator.getRequestMapper(request).getValue[T7](request, null)
      val p8 = BowlerConfigurator.getRequestMapper(request).getValue[T8](request, null)
      val p9 = BowlerConfigurator.getRequestMapper(request).getValue[T9](request, null)
      val p10 = BowlerConfigurator.getRequestMapper(request).getValue[T10](request, null)
      val p11 = BowlerConfigurator.getRequestMapper(request).getValue[T11](request, null)
      val p12 = BowlerConfigurator.getRequestMapper(request).getValue[T12](request, null)
      ParameterMapperHelper.handleErrors{func(p1,p2, p3,p4,p5,p6,p7, p8, p9,p10,p11,p12)}
    }else{
      val p1 = BowlerConfigurator.getRequestMapper(request).getValue[T1](request, nameHints(0))
      val p2 = BowlerConfigurator.getRequestMapper(request).getValue[T2](request, nameHints(1))
      val p3 = BowlerConfigurator.getRequestMapper(request).getValue[T3](request, nameHints(2))
      val p4 = BowlerConfigurator.getRequestMapper(request).getValue[T4](request, nameHints(3))
      val p5 = BowlerConfigurator.getRequestMapper(request).getValue[T5](request, nameHints(4))
      val p6 = BowlerConfigurator.getRequestMapper(request).getValue[T6](request, nameHints(5))
      val p7 = BowlerConfigurator.getRequestMapper(request).getValue[T7](request, nameHints(6))
      val p8 = BowlerConfigurator.getRequestMapper(request).getValue[T8](request, nameHints(7))
      val p9 = BowlerConfigurator.getRequestMapper(request).getValue[T9](request, nameHints(8))
      val p10 = BowlerConfigurator.getRequestMapper(request).getValue[T10](request, nameHints(9))
      val p11 = BowlerConfigurator.getRequestMapper(request).getValue[T11](request, nameHints(10))
      val p12 = BowlerConfigurator.getRequestMapper(request).getValue[T12](request, nameHints(11))
      ParameterMapperHelper.handleErrors{func(p1,p2, p3,p4,p5,p6,p7,p8,p9,p10,p11,p12)}
    }
  }

  private def validateNamehints(numOfParams: Int, nameHints: List[String]){
    if(nameHints != null && (nameHints.size > 0 && (nameHints.size > numOfParams || nameHints.size < numOfParams)))
      throw new IllegalArgumentException("for a " + numOfParams + " parameter mapping nameHint must have a size of either exactly 0 or " + numOfParams + "")
  }
}


object ParameterMapperHelper{
  def handleErrors(func: => Any){
    try {
      func
    } catch {
      case e: ClassCastException => throw new RequestMapperException("Could not map a parameter to a value! If a " +
        "parameter is not mandatory, you should consider using Option[]!")
    }
  }
}