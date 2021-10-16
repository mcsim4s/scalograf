package scalograf
package model.link

import io.circe.Decoder.Result
import io.circe.syntax._
import io.circe.{Codec, DecodingFailure, HCursor, JsonObject}

trait Link {
  def `type`: String
  def asJson: JsonObject
}

object Link {
  implicit val codec = new Codec.AsObject[Link] {

    override def encodeObject(a: Link): JsonObject = a.asJson.add("type", a.`type`.asJson)

    override def apply(c: HCursor): Result[Link] =
      c.downField("type")
        .as[String]
        .flatMap {
          case "link" => c.as[UrlLink]
          case other  => Left(DecodingFailure(s"Unknown Link type '$other'", c.history))
        }
  }
}
