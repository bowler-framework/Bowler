package org.bowlerframework.view.scalate

import org.bowlerframework.{MappedPath, Request}
import java.io.IOException

/**
 * Resolves Templates, View Templates and Layouts for a given request
 */

trait TemplateResolver{

  def getAbsoluteResource(uri: String): String

  def getAbsoluteResource(path: String, fileType: String, locale: String = null): Template


    /** Order of preference for view template resolution should be:<br/>
   * <ol>
   *  <li>TemplateSuffixSelector choice</li>
   *  <li>localisation</li>
   *  <li>file-type (mustache, ssp, jade, scaml etc)</li>
   * </ol>
   */
  def resolveViewTemplate(request: Request): Template = {
    val overridePath = TemplateRegistry.getOverrideTemplate(request.getMappedPath)
    if (None != overridePath) {
      try {
        println(TemplateRegistry.getSuffixes(request))
        return resolveResourceWithSuffix(overridePath.get, TemplateRegistry.templateTypePreference, TemplateRegistry.getSuffixes(request), request.getLocales)
      } catch {
        case e: IOException => {} // do nothing, continue to normal execution path
      }
    }

    if (!TemplateRegistry.rootViewPackageOrFolder.endsWith("/"))
      TemplateRegistry.rootViewPackageOrFolder = TemplateRegistry.rootViewPackageOrFolder + "/"
    val requestPath = request.getMappedPath.path.replaceAll(":", "_")

    var path = TemplateRegistry.rootViewPackageOrFolder + request.getMethod + requestPath
    return resolveResourceWithSuffix(path, TemplateRegistry.templateTypePreference, TemplateRegistry.getSuffixes(request), request.getLocales)
  }
  /**
   * Order of preference for layout resolution should be:<br/>
   * <ol>
   *  <li>LayoutSelector choice</li>
   *  <li>localisation</li>
   *  <li>file-type (mustache, ssp, jade, scaml etc)</li>
   * </ol>
   *
   */
  def resolveLayout(request: Request, layout: Layout): Template = {
    if (!TemplateRegistry.rootLayoutPackageOrFolder.endsWith("/"))
      TemplateRegistry.rootLayoutPackageOrFolder = TemplateRegistry.rootLayoutPackageOrFolder + "/"
    return resolveTemplate(request, TemplateRegistry.rootLayoutPackageOrFolder + layout.name)
  }

     /** Order of preference for template resolution should be (this is with an "absolute path", except for selector and locale):<br/>
   * <ol>
   *  <li>localisation</li>
   *  <li>file-type (mustache, ssp, jade, scaml etc)</li>
   * </ol>
   */
  def resolveTemplate(request: Request, path: String): Template = {
    if(request != null)
      return resolveResource(path, TemplateRegistry.templateTypePreference, request.getLocales)
    else
       return resolveResource(path, TemplateRegistry.templateTypePreference, Nil)
  }


  private def resolveResourceWithSuffix(path: String, fileTypes: List[String], suffixes: List[String], locale: List[String] = List()): Template = {
    var realPath = path
    if (realPath.endsWith("/")) {
      realPath = realPath + "index"
    }
    if (suffixes != Nil)
      realPath = realPath + "_" + suffixes(0)

    try {
      return resolveResource(realPath, TemplateRegistry.templateTypePreference, locale)
    } catch {
      case e: IOException => {
        try {
          if (!path.endsWith("/")) {
            if (suffixes != Nil)
              return resolveResource(path + "/index_" + suffixes(0), TemplateRegistry.templateTypePreference, locale)
            else
              return resolveResource(path + "/index", TemplateRegistry.templateTypePreference, locale)
          }
          else throw e
        } catch {
          case ex: IOException => {
            if (suffixes != Nil) {
              val newSuffix = suffixes.drop(1)
              return resolveResourceWithSuffix(path, fileTypes, newSuffix, locale)
            } else
              throw ex
          }
        }
      }
    }
  }

  /**
   * get potentially localised file, fallback to default if not present
   */
  private def resolveResource(path: String, fileTypes: List[String], locale: List[String] = List()): Template = {
    //println(fileTypes)
    if (fileTypes == Nil) {
      var locales = locale
      if (locale != Nil) {
        locales = locale.drop(1)
      } else
        throw new IOException("Could not find a template of type .mustache, .ssp, .jade or .scaml with path: classpath://" + path)
      return resolveResource(path, TemplateRegistry.templateTypePreference, locales)
    } else {
      try {
        if (locale != Nil)
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
}