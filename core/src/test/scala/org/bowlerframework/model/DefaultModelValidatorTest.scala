package org.bowlerframework.model

import org.scalatest.FunSuite
import com.recursivity.commons.validator.{MinIntValidator, MaxIntValidator}

/**
 * Created by IntelliJ IDEA.
 * User: wfaler
 * Date: 15/01/2011
 * Time: 23:14
 * To change this template use File | Settings | File Templates.
 */

class DefaultModelValidatorTest extends FunSuite{
  test("test validation failure"){
    val validator = new DefaultModelValidator(this.getClass)

    validator.add(new MaxIntValidator("integer", 4, {5}))
    validator.add(new MinIntValidator("integer", 6, {5}))

    val errors = validator.validate
    assert(errors != None)
    assert(errors.get.size == 2)

    assert(errors.get(0)._1 == "integer")
    assert(errors.get(0)._2 == "The Number can be at most 4")

    assert(errors.get(1)._1 == "integer")
    assert(errors.get(1)._2 == "The Number must be at least 6")
  }

  test("validation success"){
    val validator = new DefaultModelValidator(this.getClass)

    validator.add(new MaxIntValidator("integer", 7, {5}))
    validator.add(new MinIntValidator("integer", 3, {5}))

    val errors = validator.validate
    assert(errors == None)
  }

}

