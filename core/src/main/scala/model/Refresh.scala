package scalograf
package model

import cats.syntax.functor._
import io.circe.{Decoder, Encoder, Json}

import scala.concurrent.duration.FiniteDuration
import time._

sealed trait Refresh

object Refresh {
  case object Never extends Refresh
  case class Every(time: FiniteDuration) extends Refresh

  implicit val decoderNever: Decoder[Never.type] = implicitly[Decoder[Boolean]].map(_ => Never)
  implicit val decoderEvery: Decoder[Every] = implicitly[Decoder[FiniteDuration]].map(time => Every(time))

  implicit val encoder: Encoder[Refresh] = Encoder.instance[Refresh] {
    case Never       => Json.fromBoolean(false)
    case Every(time) => durationEncoder(time)
  }

  implicit val decoder: Decoder[Refresh] = List[Decoder[Refresh]](
    decoderNever.widen,
    decoderEvery.widen
  ).reduce(_ or _)
}
