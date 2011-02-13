package org.bowlerframework.jpa

import com.recursivity.jpa.Jpa._
import org.scalatest.FunSuite

/**
 * Created by IntelliJ IDEA.
 * User: wfaler
 * Date: 13/02/2011
 * Time: 22:15
 * To change this template use File | Settings | File Templates.
 */

class JpaDaoTest extends FunSuite{
  val dao = new JpaDao[MyBean, String]

  test("CRUD test"){
    val myBean = new MyBean
		myBean.id = "key"
		myBean.value ="Value"
    transaction{
      dao.create(myBean)
    }
    var bean : Option[MyBean] = None
    transaction{
      bean = dao.findById("key")
      assert(bean != None)
      assert(bean.get.value == "Value")

    }
    transaction{
      bean.get.value = "OtherValue"
      dao.update(bean.get)
    }

    bean = None
    transaction{
      bean = dao.findById("key")
      assert(bean != None)
      assert(bean.get.value == "OtherValue")
      dao.delete(bean.get)
    }

    transaction{
      assert(None == dao.findById("key"))
    }
  }

  test("findAll"){
    val myBean = new MyBean
		myBean.id = "key"
		myBean.value ="Value"
    transaction{
      dao.create(myBean)
    }

    transaction{
      val results = dao.findAll()
      assert(results.size == 1)
      assert(results(0).value == "Value")
      dao.delete(results(0))
    }

  }


}