package scalograf
package model.annotations

import io.circe.Decoder.Result
import io.circe._
import io.circe.generic.extras.Configuration

trait Annotation {
  def `type`: String

  def asJson: JsonObject
}

object Annotation {
  implicit val codecConfig = Configuration.default
  implicit val codec: Codec.AsObject[Annotation] =
    new Codec.AsObject[Annotation] {
      override def encodeObject(a: Annotation): JsonObject = a.asJson.add("type", Json.fromString(a.`type`))

      override def apply(c: HCursor): Result[Annotation] = {
        c.downField("type").as[String] match {
          case Right("tags")      => c.as[Tags]
          case Right("dashboard") => c.as[DashboardAnnotation]
          case Right(other)       => Left(DecodingFailure(s"Unknown annotation type '$other'", c.history))
          case Left(_)            => Left(DecodingFailure("Annotation object doesn't contain 'type' field", c.history))
        }
      }
    }
}
