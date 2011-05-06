package org.bowlerframework.controller

import com.recursivity.commons.bean.scalap.ClassSignature
import org.bowlerframework.jvm.{DummyRequest, DummyResponse}
import org.bowlerframework.http.BowlerFilter
import org.scalatra.test.scalatest.ScalatraFunSuite
import org.bowlerframework.{Request, Session, GET}
import org.bowlerframework.view.Renderable
import java.io.{StringReader, StringWriter}
import org.bowlerframework.view.scuery.stub.{ComposedPageComponent, SimpleTransformingComponent}
import org.bowlerframework.model.{MyBean}

/**
 * Created by IntelliJ IDEA.
 * User: wfaler
 * Date: 01/05/2011
 * Time: 02:48
 * To change this template use File | Settings | File Templates.
 */

class POSORouteMapperTest extends ScalatraFunSuite{
  val holder = this.addFilter(classOf[BowlerFilter], "/*")
  holder.setInitParameter("controllerPackage", "org.bowlerframework.stub.controller")

  test("test signature calculation"){
    val signature = ClassSignature(classOf[StubPosoController])
    val member = signature.members.find(ma => ma.name.equals("GET /poso/hello"))
    assert("/poso/hello" == POSORouteMapper.calculatePaths(member.get))
  }

  test("simple closure creation"){
    val signature = ClassSignature(classOf[StubPosoController])
    val member = signature.members.find(ma => ma.name.equals("GET /poso/hello"))
    val func = POSORouteMapper.createClosure(member.get, new StubPosoController)

    val writer = new StringWriter
    val resp = new DummyResponse(writer)
    val request = new DummyRequest(GET, "/poso/hello", Map(), null, Map("accept" -> "application/json"))

    func(request, resp)

    assert("""{"key":"greeting","value":"hello"}""" == resp.toString)
  }

  test("one arg"){
    POSORouteMapper(new PosoController, new StubPosoController)
    val list = List[Tuple2[String, String]]()

    get("/poso/page/5", list, Map("accept" -> "application/json,;q=0.9,text/plain;q=0.8,image/png,*//*;q=0.5")) {
      assert("""{"key":"id","value":"sum: 7"}""" == this.body)
    }
  }

  test("more than one arg"){
    POSORouteMapper(new PosoController, new StubPosoController)
    val list = List[Tuple2[String, String]]()

    get("/poso/twoparams?id=3&number=4", list, Map("accept" -> "application/json,;q=0.9,text/plain;q=0.8,image/png,*//*;q=0.5")) {
      assert("""{"key":"id","value":"id: 3, number: 4"}""" == this.body)
    }
  }

  test("unit return"){
    POSORouteMapper(new PosoController, new StubPosoController)
    val list = List[Tuple2[String, String]]()

    get("/poso/unit", list, Map("accept" -> "application/json,;q=0.9,text/plain;q=0.8,image/png,*//*;q=0.5")) {
      assert(204 == this.status)
    }
  }

  test("injected request and session"){
    POSORouteMapper(new PosoController, new StubPosoController)
    val list = List[Tuple2[String, String]]()

    get("/poso/request", list, Map("accept" -> "application/json,;q=0.9,text/plain;q=0.8,image/png,*//*;q=0.5")) {
      assert("""{"key":"greeting","value":"session & request"}""" == this.body)
    }
  }

  test("controller implements renderable"){
    POSORouteMapper(new PosoController, new RenderablePoso)
    val list = List[Tuple2[String, String]]()

    get("/poso/render", list) {
      println(body)
      val result = scala.xml.XML.load(new StringReader(body))
      assert("James" == ((result \ "body" \ "div" \ "table" \\ "tr")(0) \ "td")(0).text)
      assert("Mells" == ((result \ "body" \ "div" \ "table" \\ "tr")(0) \ "td")(1).text)

      assert("Hiram" == ((result \ "body" \ "div" \ "table" \\ "tr")(1) \ "td")(0).text)
      assert("Tampa" == ((result \ "body" \ "div" \ "table" \\ "tr")(1) \ "td")(1).text)
      assert("A Title" == (result \ "head" \ "title").text)
    }
  }

  test("Renderable: POST complex object - alias"){
    val controller = new RenderablePoso
    POSORouteMapper(new PosoController, controller)
    val list = List(Tuple2("myBean.id","2"), Tuple2("myBean.name", "otherBean"), Tuple2("myBean.decimal", "3.14"))

    post("/poso/bean", list, Map("accept" -> "application/json,;q=0.9,text/plain;q=0.8,image/png,*//*;q=0.5")){
      println(body)
      assert("""{"id":2,"name":"otherBean","decimal":"3.14"}""" == body)
      assert(controller.bean != null)
      assert(controller.bean.decimal.toString == "3.14")
    }
  }

  test("Renderable: POST complex object - no alias"){
    val controller = new RenderablePoso
    POSORouteMapper(new PosoController, controller)
    val list = List(Tuple2("id","3"), Tuple2("name", "otherBean"), Tuple2("decimal", "3.15"))

    post("/poso/bean", list, Map("accept" -> "application/json,;q=0.9,text/plain;q=0.8,image/png,*//*;q=0.5")){
      println(body)
      assert("""{"id":3,"name":"otherBean","decimal":"3.15"}""" == body)
      assert(controller.bean != null)
      assert(controller.bean.decimal.toString == "3.15")
    }
  }

  test("Renderable: POST complex object with multiple args - no alias"){
    val controller = new RenderablePoso
    POSORouteMapper(new PosoController, controller)
    val list = List(Tuple2("id","4"), Tuple2("name", "otherBean"), Tuple2("decimal", "3.16"))

    post("/poso/bean/8", list, Map("accept" -> "application/json,;q=0.9,text/plain;q=0.8,image/png,*//*;q=0.5")){
      println(body)
      assert("""{"id":4,"name":"otherBean","decimal":"3.16"}""" == body)
      assert(controller.bean != null)
      assert(controller.bean.decimal.toString == "3.16")
      assert(controller.id == 8L)
    }
  }


  test("POST complex object - alias"){
    val controller = new StubPosoController
    POSORouteMapper(new PosoController, controller)
    val list = List(Tuple2("myBean.id","2"), Tuple2("myBean.name", "otherBean"), Tuple2("myBean.decimal", "3.14"))

    post("/poso/bean", list, Map("accept" -> "application/json,;q=0.9,text/plain;q=0.8,image/png,*//*;q=0.5")){
      println(body)
      assert("""{"id":2,"name":"otherBean","decimal":"3.14"}""" == body)
      assert(controller.bean != null)
      assert(controller.bean.decimal.toString == "3.14")
    }
  }

  test("POST complex object - no alias"){
    val controller = new StubPosoController
    POSORouteMapper(new PosoController, controller)
    val list = List(Tuple2("id","3"), Tuple2("name", "otherBean"), Tuple2("decimal", "3.15"))

    post("/poso/bean", list, Map("accept" -> "application/json,;q=0.9,text/plain;q=0.8,image/png,*//*;q=0.5")){
      println(body)
      assert("""{"id":3,"name":"otherBean","decimal":"3.15"}""" == body)
      assert(controller.bean != null)
      assert(controller.bean.decimal.toString == "3.15")
    }
  }

  test("POST complex object with multiple args - no alias"){
    val controller = new StubPosoController
    POSORouteMapper(new PosoController, controller)
    val list = List(Tuple2("id","4"), Tuple2("name", "otherBean"), Tuple2("decimal", "3.17"))

    post("/poso/bean/9", list, Map("accept" -> "application/json,;q=0.9,text/plain;q=0.8,image/png,*//*;q=0.5")){
      println(body)
      assert("""{"id":4,"name":"otherBean","decimal":"3.17"}""" == body)
      assert(controller.bean != null)
      assert(controller.bean.decimal.toString == "3.17")
      assert(controller.id == 9L)
    }
  }

}


case class Tuple(key: String, value: String)

class PosoController extends Controller

class StubPosoController{
  var bean : MyBean = null
  var id: Long = 1L

  def `GET /poso/hello` = Tuple("greeting", "hello")
  def `GET /poso/page/:id`(id: Long) = Tuple("id", "sum: " + (id + 2L))
  def `GET /poso/unit`: Unit = {

  }

  def `GET /poso/request`(request: Request, session: Session): Tuple = {
    if(request == null)
      return Tuple("fail", "fail")
    if(session == null)
      return Tuple("fail", "fail")
    Tuple("greeting", "session & request")
  }

  def `GET /poso/twoparams`(id: Long, number: Long) = Tuple("id", "id: " + id + ", number: " + number)

  def `POST /poso/bean`(bean: MyBean): MyBean = {
    this.bean = bean
    bean
  }

  def `POST /poso/bean/:beanId`(beanId: Long, bean: MyBean): MyBean = {
    this.bean = bean
    this.id = beanId
    bean
  }
}

class RenderablePoso extends Renderable{
  var bean : MyBean = null
  var id: Long = 1L
  def `GET /poso/render` = {
    renderWith(new ComposedPageComponent(new SimpleTransformingComponent))
  }

  def `POST /poso/bean`(bean: MyBean) = {
    this.bean = bean
    render(bean)
  }

  def `POST /poso/bean/:beanId`(beanId: Long, bean: MyBean){
    this.bean = bean
    this.id = beanId
    render(bean)
  }

}