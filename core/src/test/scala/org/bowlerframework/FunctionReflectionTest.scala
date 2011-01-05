package org.bowlerframework

import org.scalatest.FunSuite
import java.lang.reflect.{TypeVariable, ParameterizedType}

/**
 * Created by IntelliJ IDEA.
 * User: wfaler
 * Date: 13/12/2010
 * Time: 02:02
 * To change this layout use File | Settings | File Templates.
 */

class FunctionReflectionTest extends FunSuite{


  test("reflect"){
    //add(3)(b => intAdd(b))

    add(List("String"))(b => {
      println(b)
    })
  }



  /*test("implied type"){
    println(add(3)(intAdd(_)))

    println(add("Hello")(println(_)))

    println(add(3, 4)(multiAdd(_, _)))

    println(add(3, "Hello: ")(multiAdd(_,_)))

    println(add(List("hello", "world"), "Wille")((b,c) => multiAdd(b,c)))

    println(add(List("hello", "world"), "Wille")((b,c) => {
      println(b)
      println(c)
    }))
  } */

  def intAdd(v: Int) = v + 4

  def multiAdd(v: Int, x: Int) = v + x

  def multiAdd(v: Int, x: String) = println("multiAdd: " + x + v)

  def multiAdd(v: List[String], x: String) = v.foreach(f => println(f + " " + x))



  def add[R](l: R)(func: R => Any)(implicit m: Manifest[R]): Any ={

    println(func.getClass.getName)
    //println(fields(0).getName)
    //println(field.)
    func(l)
  }

  def add[R,S](l: R, k: S)(func: (R,S) => Any)(implicit m: Manifest[R], t: Manifest[S]): Any ={
    println("implicit: " + m.toString + ", " + t.toString)
    m.typeArguments.foreach(p => println("generics: " + p))

    //m.
    //println(fields(0).getName)
    //println(field.)
    func(l,k)
  }


}