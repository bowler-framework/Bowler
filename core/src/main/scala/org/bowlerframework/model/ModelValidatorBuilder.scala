package org.bowlerframework.model

/**
 * Created by IntelliJ IDEA.
 * User: wfaler
 * Date: 01/02/2011
 * Time: 23:57
 * To change this template use File | Settings | File Templates.
 */

class ModelValidatorBuilder(model: AnyRef) extends DefaultModelValidator(model.getClass)