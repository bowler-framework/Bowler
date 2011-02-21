package org.bowlerframework.examples.jpa

import org.bowlerframework.view.scalate.ComponentRenderSupport
import com.recursivity.jpa.Jpa._
import collection.mutable.MutableList

/**
 * Created by IntelliJ IDEA.
 * User: wfaler
 * Date: 20/02/2011
 * Time: 23:40
 * To change this template use File | Settings | File Templates.
 */

object MakeDropdownComponent extends ComponentRenderSupport{
  def show(make: Make) = {
    transaction{
      val makes = entityManager.createQuery("from Make as m").getResultList.asInstanceOf[java.util.List[Make]]
      val list = new MutableList[Make]
      val iterator = makes.iterator
      while(iterator.hasNext)
        list += iterator.next

      if(make != null)
        render(make, list.toList)
      else render(list.toList, new Make("Yibberish that will never be selected"))
    }
  }

  def show = {
    transaction{
      val makes = entityManager.createQuery("from Make as m").getResultList.asInstanceOf[java.util.List[Make]]
      val list = new MutableList[Make]
      val iterator = makes.iterator
      while(iterator.hasNext)
        list += iterator.next

      render(list.toList, new Make("Yibberish that will never be selected"))
    }
  }
}