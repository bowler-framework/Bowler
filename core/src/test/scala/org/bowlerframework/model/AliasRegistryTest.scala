package org.bowlerframework.model

import org.scalatest.FunSuite
import com.recursivity.commons.bean.{GenericsParser, GenericTypeDefinition}
import collection.TraversableLike

/**
 * Created by IntelliJ IDEA.
 * User: wfaler
 * Date: 28/12/2010
 * Time: 02:10
 * To change this template use File | Settings | File Templates.
 */

class AliasRegistryTest extends FunSuite{

  test("Simple Alias"){
    assert("int" == AliasRegistry.getRequestAlias(getValue[Int]))
    assert("string" == AliasRegistry.getRequestAlias(getValue[String]))
  }

  test("Generified Aliases"){
    assert("list[int]" == AliasRegistry.getRequestAlias(getValue[List[Int]]))
    assert("list[integer]" == AliasRegistry.getRequestAlias(getValue[List[java.lang.Integer]]))
    assert("set[string]" == AliasRegistry.getRequestAlias(getValue[Set[String]]))
    assert("option[int]" == AliasRegistry.getRequestAlias(getValue[Option[Int]]))
    assert("collection[string]" == AliasRegistry.getRequestAlias(getValue[java.util.Collection[String]]))
  }

  test("alias for Any"){
    assert("aliasBean".equals(AliasRegistry.getModelAliasKey(getValue[AliasBean]).get))
    assert("string".equals(AliasRegistry.getModelAliasKey(getValue[String]).get))
    assert("int".equals(AliasRegistry.getModelAliasKey(getValue[Int]).get))
    assert("model".equals(AliasRegistry.getModelAliasKey(getValue[Model]).get))
  }

  test("key for Any, Seq/List/Set/java.util.Collection"){
    assert("aliasBeans".equals(AliasRegistry.getModelAliasKey(getValue[List[AliasBean]]).get))
    assert("aliasBeans".equals(AliasRegistry.getModelAliasKey(getValue[Set[AliasBean]]).get))
    assert("aliasBeans".equals(AliasRegistry.getModelAliasKey(getValue[Seq[AliasBean]]).get))
    assert("aliasBeans".equals(AliasRegistry.getModelAliasKey(getValue[java.util.Collection[AliasBean]]).get))
    assert("aliasBeans".equals(AliasRegistry.getModelAliasKey(getValue[java.util.List[AliasBean]]).get))
    assert("aliasBeans".equals(AliasRegistry.getModelAliasKey(getValue[java.util.Set[AliasBean]]).get))
  }

  test("alias for value without registered"){
    assert("aliasBean".equals(AliasRegistry.getModelAlias(AliasBean("someName", 24)).get))
  }

  test("alias for traversable without registrered"){
    assert("aliasBeans".equals(AliasRegistry.getModelAlias(List(AliasBean("someName", 24))).get))
  }

  test("registered alias for TraversableLike"){
    AliasRegistry.registerModelAlias[List[BeanWithAlias]]("aliasBeansFTW")
    assert("aliasBeansFTW".equals(AliasRegistry.getModelAlias(List(BeanWithAlias())).get))
  }

  test("register alias for bean"){
    AliasRegistry.registerModelAlias[BeanWithAlias]("abean")
    assert("abean".equals(AliasRegistry.getModelAlias(BeanWithAlias()).get))
  }

  test("alias for primitive without registered"){
    assert("long".equals(AliasRegistry.getModelAlias(23l).get))
  }


  private def getValue[T]()(implicit m: Manifest[T]): GenericTypeDefinition = {
    var typeString = m.toString.replace("[", "<")
    typeString = typeString.replace("]", ">")
    return GenericsParser.parseDefinition(typeString)
  }
}

case class BeanWithAlias()
case class AliasBean(name: String, age: Int)
case class Model()