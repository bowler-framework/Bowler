package org.bowlerframework.view

import org.bowlerframework.Request
import java.util.StringTokenizer
import scalate.ScalateViewRenderer
import org.bowlerframework.exception.HttpException

/**
 * Strict Render Strategy that adheres strictly to HTTP (not suitable for general web use where Internet Explorer
 * may be one of the allowable clients).<br/>
 *   Parses accept-header with content-types assumed in order of client-preference. Takes a mapping of content-types to
 *   ViewRenderer implementation factory functions as an argument, with sensible defaults set.
 */

class StrictRenderStrategy(mappings: Map[String, () => ViewRenderer] =
                           Map[String, () => ViewRenderer]("application/json" -> {() => new JsonViewRenderer},
                           "text/html" -> {() => new ScalateViewRenderer},
                           "application/xhtml+xml" -> {() => new ScalateViewRenderer},
                           "*/*" -> {() => new JsonViewRenderer})) extends RenderStrategy{
  def resolveViewRenderer(request: Request): ViewRenderer = {
    val accept = {
      try{
        request.getAccept
      }catch{
        case e: NoSuchElementException => throw new HttpException(406)
      }
    }

    val tokenizer = {
      if(accept.indexOf(";") > 0)
        new StringTokenizer(accept.substring(0, accept.indexOf(";")).toLowerCase, ",")
      else
        new StringTokenizer(accept.toLowerCase, ",")
    }

    while(tokenizer.hasMoreTokens){
      val acceptContentType = tokenizer.nextToken.trim
      val opt = mappings.get(acceptContentType)
      opt match{
        case Some(viewRenderer) => return viewRenderer()
        case None => {}
      }

    }
    throw new HttpException(406)
  }
}