package org.bowlerframework.model

import org.scalatest.FunSuite

/**
 * Created by IntelliJ IDEA.
 * User: wfaler
 * Date: 16/02/2011
 * Time: 00:38
 * To change this template use File | Settings | File Templates.
 */

class ModelValidatorBuilderTest extends FunSuite {
  test("test validatorBuilder") {
    assert(None == ModelValidatorBuilder(classOf[ValidatableBean]))

    ModelValidatorBuilder.registerValidatorBuilder(classOf[ValidatableBean], new ValidatableBuilder)

    assert(None != ModelValidatorBuilder(classOf[ValidatableBean]))

    assert(ModelValidatorBuilder(classOf[ValidatableBean]).get.isInstanceOf[ValidatableBuilder])
    val builder1 = ModelValidatorBuilder(classOf[ValidatableBean]).get.asInstanceOf[ValidatableBuilder]

    assert(builder1.initialize(ValidatableBean("new")) != builder1.initialize(ValidatableBean("new")))
  }
}


case class ValidatableBean(name: String)

class ValidatableBuilder extends ModelValidatorBuilder[ValidatableBean] {
  def initialize(bean: ValidatableBean): ModelValidator = {
    val builder = new DefaultModelValidator(classOf[ValidatableBean])
    return builder
  }
}