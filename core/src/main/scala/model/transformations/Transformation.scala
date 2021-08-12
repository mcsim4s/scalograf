package scalograf
package model.transformations

import io.circe.Decoder.Result
import io.circe.generic.extras.Configuration
import io.circe.syntax._
import io.circe.{Codec, DecodingFailure, HCursor, JsonObject}

trait Transformation {
  def id: String
  def toJson: JsonObject
}

object Transformation {
  implicit val config = Configuration.default
  implicit val codec = new Codec.AsObject[Transformation] {
    override def encodeObject(t: Transformation): JsonObject =
      JsonObject(
        "id" -> t.id.asJson,
        "options" -> t.toJson.asJson
      )

    override def apply(c: HCursor): Result[Transformation] =
      c.downField("id").as[String].flatMap {
        case "organize"           => c.downField("options").as[Organize]
        case "filterFieldsByName" => c.downField("options").as[FilterFieldsByName]
        case "sortBy"             => c.downField("options").as[Sort]
        case other                => Left(DecodingFailure(s"Unknown transformation type '$other'", c.history))
      }
  }
}
