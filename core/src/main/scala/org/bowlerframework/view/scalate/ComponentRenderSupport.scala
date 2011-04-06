package org.bowlerframework.view.scalate

import org.fusesource.scalate.util.{Resource, FileResourceLoader}
import java.io.{StringWriter, PrintWriter}
import org.fusesource.scalate.{DefaultRenderContext, TemplateEngine}
import org.bowlerframework.RequestScope
import org.bowlerframework.model.ViewModelBuilder


trait ComponentRenderSupport {

  def render(models: Any*): String = {
    renderMap(ViewModelBuilder(models.toSeq).toMap)
  }

  def renderWithNamed(action: String, models: Any*) {
    render(ViewModelBuilder(models.toSeq), action)
  }

  def renderMap(model: Map[String, Any], action: String = "index"): String = {
    var uri = "/" + this.getClass.getName.replace(".", "/")
    uri = uri.replace("$", "") + "_" + action
    render(uri, model)
  }

  private def render(uri: String, model: Map[String, Any]): String = {
    val writer = new StringWriter
    val pw = new PrintWriter(writer)
    val template = ComponentRenderSupport.resolver.resolveTemplate(RequestScope.request, uri)
    val context = new DefaultRenderContext(template.uri, ComponentRenderSupport.engine, pw)
    context.numberFormat = RenderEngine.numberFormat
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