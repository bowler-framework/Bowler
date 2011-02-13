package org.bowlerframework.jpa


import javax.persistence.{Entity, Table, Id, Column}
import reflect.BeanProperty


@Entity
@Table(name="test_beans")
class MyBean{
  @Id
  @Column
  @BeanProperty
  var id: String = null
  @Column
  @BeanProperty
  var value: String = null
}