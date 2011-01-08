package org.bowlerframework.view.scalate

import org.bowlerframework.Request
import com.recursivity.commons.{StringInputStreamReader, ClasspathResourceResolver}
import java.io.{IOException, InputStream}

/**
 * Created by IntelliJ IDEA.
 * User: wfaler
 * Date: 07/01/2011
 * Time: 23:58
 * To change this template use File | Settings | File Templates.
 */

class ClasspathTemplateResolver extends TemplateResolver with StringInputStreamReader{


  def resolveViewTemplate(request: Request) = null

  def resolveLayout(request: Request, layout: Layout): Template ={
    if(!TemplateRegistry.rootLayoutPackageOrFolder.endsWith("/"))
      TemplateRegistry.rootLayoutPackageOrFolder = TemplateRegistry.rootLayoutPackageOrFolder + "/"
    return resolveTemplate(request, TemplateRegistry.rootLayoutPackageOrFolder + layout.name)
  }

  def resolveTemplate(request: Request, path: String): Template = {
    return resolveResource(path, TemplateRegistry.templateTypePreference, request.getLocales)
  }

    /**
   * Gets a resource with no fallback to default if localised file does not exist
   */
  private def getAbsoluteResource(path: String, fileType: String, locale: String = null): Template = {
    var is: InputStream = null
    try {
      var realPath: String = null
      if(locale == null)
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

  /**
  * get potentially localised file, fallback to default if not present
  */
  private def resolveResource(path: String, fileTypes: List[String], locale: List[String] = List()): Template = {
    //println(fileTypes)
    if (fileTypes == Nil){
      var locales = locale
      if(locale != Nil){
        locales = locale.drop(1)
      }else
        throw new IOException("Could not find a template of type .mustache, .ssp, .jade or .scaml with path: classpath://" + path)
      return resolveResource(path, TemplateRegistry.templateTypePreference, locales)
    }else {
      try {
        if(locale != Nil)
          return getAbsoluteResource(path, fileTypes(0), locale.head)
        else
          return getAbsoluteResource(path, fileTypes(0), null)
      } catch {
        case e: NullPointerException => {
          val types = fileTypes.drop(1)
          return resolveResource(path, types, locale)
        }
      }
    }
  }


  /**
   * get potentially localised file, fallback to default if not present
   */
 /* private def resolveResourceByType(path: String, fileType: String, locale: List[String] = List())(op: InputStream => Any): Any = {
    if (locale == Nil)
      getAbsoluteResource(path, fileType, null) {is => op(is)}
    else {
      try {
        getAbsoluteResource(path, fileType, locale.head) {is => op(is)}
      } catch {
        case e: NullPointerException => {
          val localeList = locale.drop(1)
          resolveResourceByType(path, fileType, localeList) {is => op(is)}
        }
      }
    }
  } */
}