package org.bowlerframework.view

import org.bowlerframework.Request
import java.util.StringTokenizer
import scalate.ScalateViewRenderer
import org.bowlerframework.exception.HttpException
import util.matching.Regex
import util.control.Breaks

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
    request.getAccept match {
      case Some(a) => {
        val orderedMediaTypes = AcceptHeaderParser.parseAndOrder(a.toLowerCase);
        for(mt <- orderedMediaTypes) {
          mappings.get(mt.mediaType + "/" + mt.mediaSubType) match {
            case Some(renderer) => {
              return renderer.apply()
            }
            case None => {}
          }
        }
        throw new HttpException(406)
      }
      case None => new ScalateViewRenderer
    }
  }
}

import util.parsing.combinator._

object AcceptHeaderParser extends JavaTokenParsers {
  lazy val accept: Parser[List[AcceptHeader]] = rep1sep(acceptEntry, ",")
  lazy val acceptEntry: Parser[AcceptHeader] =
    (mediaType <~ "/") ~ mediaSubType ~ opt(qualityFactor) ^^ {
    case t ~ st ~ Some(q) => AcceptHeader(t, st, q.toFloat)
    case t ~ st ~ None => AcceptHeader(t, st, 1.0F)
  }
  lazy val wordRegex = """[\w+\-*]*""".r
  lazy val mediaType = wordRegex
  lazy val mediaSubType = wordRegex
  lazy val qualityFactor = ";" ~> "q" ~> "=" ~> floatingPointNumber

  def parseAndOrder(input: String): List[AcceptHeader] = {
    parseAll(accept, input).getOrElse(Nil).sortWith((a1, a2) => (a1 > a2))
  }

  def parse(input:String): List[AcceptHeader] = parseAll(accept, input).getOrElse(Nil)

}

case class AcceptHeader(mediaType: String, mediaSubType: String, qualityFactor: Float) extends Ordered[AcceptHeader] {
  def compare(that: AcceptHeader): Int = {
     if (this.qualityFactor > that.qualityFactor) 1
     else if (this.qualityFactor < that.qualityFactor) -1
     else (mediaType, mediaSubType, that.mediaType, that.mediaSubType) match {
       case ("*", _, _, _) => -1
       case (_, _, _, "*") => 1
       case (_, "*", _, _) => -1
       case (_, _, "*", _) => 1
       case (_, _, _, _) => 0
     }
  }
}