package scalograf
package model.panels.gauge

import io.circe.Decoder.Result
import io.circe.{Codec, HCursor, Json}
import io.circe.syntax._

sealed trait GaugeFields

object GaugeFields {
  case object Numeric extends GaugeFields
  case object All extends GaugeFields
  case class Exact(fieldName: String) extends GaugeFields

  implicit val codec = new Codec[GaugeFields] {
    override def apply(a: GaugeFields): Json =
      a match {
        case Numeric          => "".asJson
        case All              => "/.*/".asJson
        case Exact(fieldName) => fieldName.asJson
      }

    override def apply(c: HCursor): Result[GaugeFields] =
      c.as[String].map {
        case ""     => Numeric
        case "/.*/" => All
        case other  => Exact(other)
      }
  }
}
