package org.bowlerframework.view.scalate

import org.fusesource.scalate.util.{Resource, FileResourceLoader}
import java.io.{StringWriter, PrintWriter}
import org.fusesource.scalate.{DefaultRenderContext, TemplateEngine}
import org.bowlerframework.RequestScope

/**
 * Created by IntelliJ IDEA.
 * User: wfaler
 * Date: 23/01/2011
 * Time: 02:09
 * To change this template use File | Settings | File Templates.
 */

trait ComponentRenderSupport {

  def render(model: Map[String, Any], action: String = "index"): String = {
    var uri = "/" + this.getClass.getName.replace(".", "/")
    uri = uri.replace("$", "") + "_" + action
    println(this.getClass.getName)
    println(uri)
    render(uri, model)
  }

  private def render(uri: String, model: Map[String,Any]): String = {
    val writer = new StringWriter
    val pw = new PrintWriter(writer)
    val template = ComponentRenderSupport.resolver.resolveTemplate(RequestScope.request, uri)
    val context = new DefaultRenderContext(template.uri, ComponentRenderSupport.engine, pw)
    context.render(template.uri, model)
    return writer.toString
  }
}

object ComponentRenderSupport {
  private val engine = new TemplateEngine
  private val resolver = new ClasspathTemplateResolver
  engine.allowCaching = true
  engine.allowReload = false
  engine.resourceLoader = new FileResourceLoader {
    override def resource(uri: String): Option[Resource] = {
      Some(Resource.fromText(uri, resolver.getAbsoluteResource(uri)))
    }
  }
}