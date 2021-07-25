package scalograf
package model

import io.circe.Decoder.Result
import io.circe.generic.extras.semiauto.{deriveConfiguredDecoder, deriveConfiguredEncoder}
import io.circe.syntax._
import io.circe.{Codec, HCursor, Json}

case class Dashboard(
    id: Option[Long] = None,
    uid: Option[String] = None,
    style: String = "default",
    tags: List[String] = List.empty,
    timezone: String = "browser",
//    templating: Templating,
    title: String = "",
    schemaVersion: Long = 0,
    editable: Boolean = true,
    annotations: List[Annotation] = List.empty,
    version: Long = 0,
    links: List[Link] = List.empty,
    panels: List[Panel] = List.empty,
//    timePicker: List[TimePicker] = List.empty,
    time: Time = Time.default
)

object Dashboard {
  implicit val dashboardCodec = new Codec[Dashboard] {
    override def apply(d: Dashboard): Json =
      deriveConfiguredEncoder[Dashboard]
        .mapJsonObject { obj =>
          obj("annotations") match {
            case Some(value) => obj.add("annotations", Json.obj("list" -> value))
            case None        => obj
          }
        }
        .apply(d)

    override def apply(c: HCursor): Result[Dashboard] =
      deriveConfiguredDecoder[Dashboard]
        .prepare(_.withFocus(_.mapObject(obj => {
          obj("annotations") match {
            case Some(value) =>
              value.asObject.flatMap(_("list")) match {
                case Some(list) => obj.add("annotations", list)
                case None       => obj
              }
            case None => obj
          }
        })))
        .apply(c)
  }
}
