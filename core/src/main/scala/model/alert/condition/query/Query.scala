package scalograf
package model.alert.condition.query

import model.time._

import io.circe.syntax._
import io.circe.{Decoder, DecodingFailure, Encoder, JsonObject}

import scala.concurrent.duration.FiniteDuration

case class Query(label: String, from: Time, window: FiniteDuration)

object Query {
  implicit val encoder = Encoder.AsObject.instance[Query] { q =>
    JsonObject(
      "params" -> List(q.label.asJson, q.window.asJson, q.from.asJson).asJson
    )
  }

  implicit val decoder = Decoder.instance[Query] { cursor =>
    val array = cursor.downField("params")
    if (array.succeeded) {
      array.downN(0).as[String].flatMap { label =>
        array.downN(1).as[FiniteDuration].flatMap { window =>
          array.downN(2).as[Time].map(from => Query(label, from, window))
        }
      }
    } else {
      Left(DecodingFailure("Query must be an array", array.history))
    }
  }
}
