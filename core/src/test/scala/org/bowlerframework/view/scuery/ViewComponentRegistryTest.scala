package org.bowlerframework.view.scuery

import org.scalatest.FunSuite
import org.bowlerframework.jvm.DummyRequest
import org.bowlerframework.view.ViewPath
import org.bowlerframework.{MappedPath, GET}
import stub.{ExtendingComponent, MySimpleComponent}

/**
 * Created by IntelliJ IDEA.
 * User: wfaler
 * Date: 24/04/2011
 * Time: 00:28
 * To change this template use File | Settings | File Templates.
 */

class ViewComponentRegistryTest extends FunSuite{



  test("register for request"){
    val request = new DummyRequest(GET, "/componentRegistryRequest", Map(), null)
    val component = new MySimpleComponent
    ViewComponentRegistry.register(request, component)

    ViewComponentRegistry(request, Map[String, Any]()) match{
      case None => fail
      case Some(c) => assert(c == component)
    }
  }

  test("register for ViewPath"){
    val localLookup: Function1[Map[String, Any], MarkupContainer] = (map) => {println("call local");new MySimpleComponent}
    ViewComponentRegistry.register(ViewPath(GET, MappedPath("/componentRegistryPath")), localLookup(_))

    val request = new DummyRequest(GET, "/componentRegistryPath", Map(), null)
    request.setMappedPath(MappedPath("/componentRegistryPath"))
    ViewComponentRegistry(request, Map[String, Any]()) match{
      case None => fail
      case Some(c) => assert(c.isInstanceOf[MySimpleComponent])
    }
  }

  test("viewPath AND request (request should trump viewpath)"){
    val request = new DummyRequest(GET, "/componentRegistryPath", Map(), null)
    request.setMappedPath(MappedPath("/componentRegistryPath"))
    ViewComponentRegistry.register(request, new ExtendingComponent)

    ViewComponentRegistry(request, Map[String, Any]()) match{
      case None => fail
      case Some(c) => assert(c.isInstanceOf[ExtendingComponent])
    }

  }

}