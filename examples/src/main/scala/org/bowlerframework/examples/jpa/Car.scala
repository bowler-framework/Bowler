package org.bowlerframework.examples.jpa

import reflect.BeanProperty
import javax.persistence.{Id, Column, Entity, Table}

/**
 * Created by IntelliJ IDEA.
 * User: wfaler
 * Date: 20/02/2011
 * Time: 20:08
 * To change this template use File | Settings | File Templates.
 */

@Entity
@Table
class Car{

  @Id
  @Column
  @BeanProperty
  var id: Long = 0

}