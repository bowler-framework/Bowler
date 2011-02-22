package org.bowlerframework.view.scalate

import com.recursivity.commons.{StringInputStreamReader, ClasspathResourceResolver}
import java.io.{InputStream}


class ClasspathTemplateResolver extends TemplateResolver with StringInputStreamReader {

  /**
   * Gets a resource with no fallback to default if localised file does not exist
   */
   def getAbsoluteResource(path: String, fileType: String, locale: String = null): Template = {
    var is: InputStream = null
    try {
      var realPath: String = null
      if (locale == null)
        realPath = path + fileType
      else
        realPath = path + "_" + locale + fileType
      val obj = new ClasspathResourceResolver
      is = obj.getClass.getResourceAsStream(realPath)
      val templateString = this.load(is)
      return Template(realPath, templateString)
    } finally {
      is.close
    }
  }



  def getAbsoluteResource(uri: String): String = {
    val obj = this
    var is: InputStream = null
    try {
      is = obj.getClass.getResourceAsStream(uri)
      return this.load(is)
    } finally {
      is.close
    }
  }

}