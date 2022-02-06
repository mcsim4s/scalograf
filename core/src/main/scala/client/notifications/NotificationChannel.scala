package scalograf
package client.notifications

import io.circe.syntax._
import io.circe.{Decoder, DecodingFailure, Encoder, JsonObject}

import java.time.Instant

case class NotificationChannel(
    id: Option[Int] = None,
    uid: Option[String],
    name: String,
    isDefault: Boolean = false,
    sendReminder: Option[Boolean] = None,
    disableResolveMessage: Option[Boolean] = None,
    created: Option[Instant] = None,
    updated: Option[Instant] = None,
    settings: NotificationChannel.Settings
)

object NotificationChannel {
  trait Settings {
    def `type`: String
    def obj: JsonObject
  }

  implicit val encoder: Encoder.AsObject[NotificationChannel] =
    Encoder.AsObject.instance { channel =>
      JsonObject(
        "id" -> channel.id.asJson,
        "uid" -> channel.uid.asJson,
        "name" -> channel.name.asJson,
        "isDefault" -> channel.isDefault.asJson,
        "sendReminder" -> channel.sendReminder.asJson,
        "disableResolveMessage" -> channel.disableResolveMessage.asJson,
        "created" -> channel.created.asJson,
        "updated" -> channel.updated.asJson,
        "type" -> channel.settings.`type`.asJson,
        "settings" -> channel.settings.obj.asJson
      )
    }

  implicit val decoder: Decoder[NotificationChannel] =
    Decoder.instance[NotificationChannel] { c =>
      c.downField("type")
        .as[String]
        .flatMap {
          case "email" => c.downField("settings").as[EmailSettings]
          case other   => Left(DecodingFailure(s"Unknown notification channel type '$other'", c.history))
        }
        .flatMap { settings =>
          for {
            id <- c.downField("id").as[Option[Int]]
            uid <- c.downField("uid").as[Option[String]]
            name <- c.downField("name").as[String]
            isDefault <- c.downField("isDefault").as[Boolean]
            sendReminder <- c.downField("sendReminder").as[Option[Boolean]]
            disableResolveMessage <- c.downField("disableResolveMessage").as[Option[Boolean]]
            created <- c.downField("created").as[Option[Instant]]
            updated <- c.downField("updated").as[Option[Instant]]
          } yield NotificationChannel(
            id = id,
            uid = uid,
            name = name,
            isDefault = isDefault,
            sendReminder = sendReminder,
            disableResolveMessage = disableResolveMessage,
            created = created,
            updated = updated,
            settings = settings
          )
        }
    }
}
