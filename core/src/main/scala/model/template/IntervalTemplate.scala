package scalograf
package model.template

import model.time._

import io.circe.generic.extras.Configuration
import io.circe.syntax._
import io.circe.{Decoder, Encoder, JsonObject}

import scala.concurrent.duration.FiniteDuration

case class IntervalTemplate(
    values: List[FiniteDuration],
    autoCount: Option[Int] = None,
    autoMin: Option[FiniteDuration] = None,
    auto: Boolean = false
) extends Template.Type {
  override def `type`: String = "interval"

  override def asJson: JsonObject = IntervalTemplate.encoder.encodeObject(this)
}

object IntervalTemplate {

  implicit val codecConfig = Configuration.default
  implicit val encoder = Encoder.AsObject.instance[IntervalTemplate] { interval =>
    JsonObject(
      "refresh" -> 2.asJson,
      "query" -> interval.values.asJson.asArray.map(_.flatMap(_.asString).mkString(",")).asJson,
      "auto_count" -> interval.autoCount.asJson,
      "auto_min" -> interval.autoMin.asJson,
      "auto" -> interval.auto.asJson
    )

  }

  implicit val decoder = Decoder.instance[IntervalTemplate] { c =>
    for {
      queryString <- c.downField("query").as[Option[String]]
      values <-
        queryString
          .getOrElse("")
          .split(",")
          .map(_.asJson.as[FiniteDuration])
          .foldRight[Decoder.Result[List[FiniteDuration]]](Right(List.empty)) {
            case (elem, acc) =>
              acc.flatMap(list => elem.map(i => i :: list))
          }

      autoCount <- c.downField("auto_count").as[Option[Int]]
      autoMin <- c.downField("auto_min").as[Option[FiniteDuration]]
      auto <- c.downField("auto").as[Option[Boolean]]
    } yield IntervalTemplate(
      values = values,
      autoCount = autoCount,
      autoMin = autoMin,
      auto = auto.getOrElse(false)
    )
  }
}
