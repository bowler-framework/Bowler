package org.bowlerframework.view.scalate

import com.recursivity.commons.{StringInputStreamReader, ClasspathResourceResolver}
import collection.mutable.HashMap
import java.io.{IOException, InputStream}


class ClasspathTemplateResolver extends TemplateResolver with StringInputStreamReader {

  /**
   * Gets a resource with no fallback to default if localised file does not exist
   */
  def getAbsoluteResource(path: String, fileType: String, locale: String = null): Template = {
    var realPath: String = null
      if (locale == null)
        realPath = path + fileType
      else
        realPath = path + "_" + locale + fileType
      try{
        val template = ClasspathTemplateResolver.templates(realPath)
        return template.getOrElse(throw new IOException("No Template with path " + realPath))
      }catch{
        case e: NoSuchElementException => {
          var is: InputStream = null
          try {
            val obj = new ClasspathResourceResolver
            is = obj.getClass.getResourceAsStream(realPath)
            val templateString = this.load(is)
            val tmpl = Template(realPath, templateString)
            if(fileType != ".html" && fileType != ".xml" && fileType != ".xhtml")
              ClasspathTemplateResolver.templates.put(realPath, Some(tmpl))
            return tmpl
          }catch{
            case ie: IOException => {
              ClasspathTemplateResolver.templates.put(realPath, None)
              throw ie
            }
          }finally {
            is.close
          }
        }
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

object ClasspathTemplateResolver{
  private val templates = new HashMap[String, Option[Template]]
}