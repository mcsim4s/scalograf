package scalograf
package model.template

import io.circe.Decoder.Result
import io.circe.syntax._
import io.circe.{Codec, HCursor, JsonObject}

case class CustomTemplate(
    values: List[String] = List.empty
) extends Template.Type {
  override def `type`: String = "custom"

  override def asJson: JsonObject = CustomTemplate.codec.encodeObject(this)
}

object CustomTemplate {
  implicit val codec: Codec.AsObject[CustomTemplate] = new Codec.AsObject[CustomTemplate] {
    override def encodeObject(t: CustomTemplate): JsonObject =
      JsonObject(
        "query" -> t.values.mkString(",").asJson
      )

    override def apply(c: HCursor): Result[CustomTemplate] =
      c.downField("query").as[String].map { query =>
        CustomTemplate(
          values = query.split(",").toList
        )
      }
  }
}
