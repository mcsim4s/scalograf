package scalograf
package model.panels

import io.circe.syntax._
import io.circe.Decoder.Result
import io.circe.{Codec, DecodingFailure, HCursor, JsonObject}
import io.circe.generic.extras.Configuration

sealed trait ScaleDistribution {
  def `type`: String
}

object Linear extends ScaleDistribution {
  override def `type`: String = "linear"
}

case class LogScale(log: Int) extends ScaleDistribution {
  override def `type`: String = "log"
}

object ScaleDistribution {
  implicit val config: Configuration = Configuration.default.withDefaults
  implicit val codecScaleDistribution: Codec.AsObject[ScaleDistribution] = new Codec.AsObject[ScaleDistribution] {
    override def apply(c: HCursor): Result[ScaleDistribution] =
      c.downField("type").as[String].flatMap {
        case "linear" => Right(Linear)
        case "log"    => c.downField("log").as[Int].map(LogScale.apply)
        case other    => Left(DecodingFailure(s"Unknown scale distribution type: $other", c.history))
      }

    override def encodeObject(d: ScaleDistribution): JsonObject =
      d match {
        case Linear        => JsonObject("type" -> "linear".asJson)
        case LogScale(log) => JsonObject("type" -> "log".asJson, "log" -> log.asJson)
      }
  }
}
