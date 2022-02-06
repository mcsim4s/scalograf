package scalograf
package client.notifications

import io.circe.syntax._
import io.circe.{Decoder, Encoder, JsonObject}

case class EmailSettings(
    addresses: List[String]
) extends NotificationChannel.Settings {
  override def `type`: String = "email"

  override def obj: JsonObject = EmailSettings.encoder.encodeObject(this)
}

object EmailSettings {
  implicit val encoder: Encoder.AsObject[EmailSettings] = Encoder.AsObject.instance[EmailSettings] { settings =>
    JsonObject(
      "addresses" -> settings.addresses.mkString(";").asJson
    )
  }
  implicit val decoder: Decoder[EmailSettings] = Decoder.instance[EmailSettings] { json =>
    for {
      addresses <- json.downField("addresses").as[Option[String]]
    } yield EmailSettings(
      addresses = addresses.map(_.split(";").toList).getOrElse(List.empty)
    )
  }
}
