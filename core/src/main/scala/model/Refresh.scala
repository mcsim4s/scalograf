package scalograf
package model

import cats.syntax.functor._
import io.circe.{Decoder, Encoder, Json}

sealed trait Refresh

object Refresh {
  case object Never extends Refresh
  case class Every(time: String) extends Refresh

  implicit val decoderNever: Decoder[Never.type] = implicitly[Decoder[Boolean]].map(_ => Never)
  implicit val decoderEvery: Decoder[Every] = implicitly[Decoder[String]].map(time => Every(time))

  implicit val encoder = Encoder.instance[Refresh] {
    case Never       => Json.fromBoolean(false)
    case Every(time) => Json.fromString(time)
  }

  implicit val decoder: Decoder[Refresh] = List[Decoder[Refresh]](
    decoderNever.widen,
    decoderEvery.widen
  ).reduce(_ or _)
}
