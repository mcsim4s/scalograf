package scalograf
package model.alert.condition

import io.circe.syntax._
import io.circe.{Decoder, DecodingFailure, Encoder}
import scalograf.model.alert.condition.query.QueryCondition

trait Condition {
  def `type`: String
}

object Condition {
  implicit val encoder = Encoder.AsObject.instance[Condition] {
    case q: QueryCondition => q.asJsonObject.add("type", q.`type`.asJson)
    case other             => throw new IllegalStateException(s"Unknown condition type: $other")
  }

  implicit val decoder = Decoder.instance[Condition] { cursor =>
    cursor.downField("type").as[String] match {
      case Right("query") => cursor.as[QueryCondition]
      case Right(other)   => Left(DecodingFailure(s"Unknown Condition type '$other'", cursor.history))
      case Left(_)        => Left(DecodingFailure("Condition object doesn't contain 'type' field", cursor.history))
    }
  }
}
