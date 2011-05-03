package org.bowlerframework.controller

import com.recursivity.commons.bean.scalap.{Member, Def, ClassSignature}
import org.bowlerframework.view.Renderable
import org.bowlerframework.model.JsonRequestMapper
import org.bowlerframework._
import java.util.StringTokenizer

/**
 * Created by IntelliJ IDEA.
 * User: wfaler
 * Date: 01/05/2011
 * Time: 01:10
 * To change this template use File | Settings | File Templates.
 */

object POSORouteMapper extends Renderable{

  def apply(controller: Controller, service: AnyRef){
    val signature: ClassSignature = ClassSignature(service.getClass)
    signature.members.foreach(member =>{
       member match{
         case Member(Def, _, _, _) => {
          val defName: String = member.name
          if(defName.startsWith("GET ") || defName.startsWith("PUT ") || defName.startsWith("POST ") || defName.startsWith("DELETE "))
            registerMember(controller, member, service)
         }
         case _ => {}
       }
    })
  }

  def registerMember(controller: Controller, member: Member, service: AnyRef){
    val closure: Function2[Request, Response, Any] = createClosure(member, service)
    val path = calculatePaths(member)

    if(member.name.startsWith("GET "))
      controller.get(path)((request, response) => closure(request, response))
    else if(member.name.startsWith("POST "))
      controller.post(path)((request, response) => closure(request, response))
    else if(member.name.startsWith("PUT "))
      controller.put(path)((request, response) => closure(request, response))
    else if(member.name.startsWith("DELETE "))
      controller.delete(path)((request, response) => closure(request, response))
  }

  def calculatePaths(member: Member): String = {
    val tokenizer = new StringTokenizer(member.name, " ")
    tokenizer.nextToken
    return tokenizer.nextToken
  }

  def createClosure(member: Member, service: AnyRef): Function2[Request, Response, Any] = {
    val cls: Class[_] = service.getClass
    val argTypes = member.parameters.map(_.paramType.definedClass)
    val methods = cls.getMethods.filter(p => p.getName.equals(member.reflectedName))
    val function = methods.find(method => {
      if(member.returnType.clazz == "scala.Unit" || (method.getReturnType == member.returnType.definedClass)){
        if(method.getParameterTypes.length == argTypes.size){
          var correct = true
          var i = 0
          method.getParameterTypes.foreach(param =>{
            if(!member.parameters(i).paramType.definedClass.isAssignableFrom(param))
              false
            else{
              i = i + 1
            }
          })
          correct
        }else
          false
      }else{
        false
      }
    })

    val isRenderable = classOf[Renderable].isAssignableFrom(service.getClass)

    function match{
      case Some(m) => {
        val closure = {(request: Request, response: Response) => {
          val mapper = BowlerConfigurator.getRequestMapper(request)
          val args: Array[java.lang.Object] = {
            if(member.parameters == 0)
              Array[java.lang.Object]()
            else if(mapper.isInstanceOf[JsonRequestMapper] && !(request.getMethod == GET || request.getMethod == DELETE))
              member.parameters.map(parameter => mapper.getValueWithTypeDefinition(parameter.paramType, request)).toArray.asInstanceOf[Array[java.lang.Object]]
            else
              member.parameters.map(parameter => mapper.getValueWithTypeDefinition(parameter.paramType, request, parameter.name)).toArray.asInstanceOf[Array[java.lang.Object]]
          }
          if(!isRenderable){
            if(member.returnType.clazz != "scala.Unit"){
              if(m.getParameterTypes.length == 0)
                render(request, response, m.invoke(service))
              else{
                render(request, response, m.invoke(service, args: _* ))
              }
            }else{
              if(m.getParameterTypes.length == 0){
                m.invoke(service)
                render(request, response)
              }else{
                m.invoke(service, args: _* )
                render(request, response)
              }
            }
          }else{
            if(m.getParameterTypes.length == 0)
              m.invoke(service)
            else{
              m.invoke(service, args: _* )
            }

          }
        }}
        return closure
      }
      case None => throw new IllegalStateException("cannot resolve a function that matches the definition " + member)
    }
  }
}