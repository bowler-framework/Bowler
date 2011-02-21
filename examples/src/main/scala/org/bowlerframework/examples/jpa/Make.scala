package org.bowlerframework.examples.jpa

import reflect.BeanProperty
import javax.persistence._

/**
 * Created by IntelliJ IDEA.
 * User: wfaler
 * Date: 20/02/2011
 * Time: 22:35
 * To change this template use File | Settings | File Templates.
 */

@Entity
@Table
class Make(make: String){

  def this() = this(null)

  @Id
  @Column
  @BeanProperty
  var id: String = make

  @BeanProperty
  @OneToMany(mappedBy="make")
  var models: java.util.Set[Car] = new java.util.HashSet[Car]

  override def toString = id
}