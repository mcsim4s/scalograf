package scalograf
package model.styles

import io.circe.Decoder.Result
import io.circe.{Codec, DecodingFailure, HCursor, Json, JsonObject}

trait Style {
  def `type`: String
  def asJson: JsonObject
}

object Style {
  implicit val codec: Codec.AsObject[Style] =
    new Codec.AsObject[Style] {
      override def encodeObject(a: Style): JsonObject = a.asJson.add("type", Json.fromString(a.`type`))

      override def apply(c: HCursor): Result[Style] = {
        c.downField("type").as[String] match {
          case Right("string") => c.as[StringStyle]
          case Right("number") => c.as[NumberStyle]
          case Right("hidden") => c.as[HiddenStyle]
          case Right("date")   => c.as[DateStyle]
          case Right(other)    => Left(DecodingFailure(s"Unknown style type '$other'", c.history))
          case Left(_)         => Left(DecodingFailure("Style object doesn't contain 'type' field", c.history))
        }
      }
    }
}
