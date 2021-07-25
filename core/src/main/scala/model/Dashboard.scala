package scalograf
package model

import io.circe.Decoder.Result
import io.circe.generic.extras.Configuration
import io.circe.generic.extras.semiauto.{deriveConfiguredDecoder, deriveConfiguredEncoder}
import io.circe.{Codec, HCursor, Json}

case class Dashboard(
    annotations: List[Annotation] = List.empty,
    description: Option[String] = None,
    editable: Boolean = true,
    id: Option[Long] = None,
    links: List[Link] = List.empty,
    panels: List[Panel] = List.empty,
    refresh: Boolean = true,
    schemaVersion: Long = 0,
    style: String = "default",
    tags: List[String] = List.empty,
    templating: List[Template] = List.empty,
    time: Time = Time.default,
    timepicker: TimePicker = TimePicker.empty,
    timezone: String = "browser",
    title: String = "",
    uid: Option[String] = None,
    version: Long = 0
)

object Dashboard {
  implicit val codecConfig = Configuration.default
  implicit val codec = new Codec[Dashboard] {
    override def apply(d: Dashboard): Json =
      deriveConfiguredEncoder[Dashboard]
        .mapJsonObject(encodeAsListObject("annotations"))
        .mapJsonObject(encodeAsListObject("templating"))
        .apply(d)

    override def apply(c: HCursor): Result[Dashboard] =
      deriveConfiguredDecoder[Dashboard]
        .prepare(decodeAsListObject("annotations"))
        .prepare(decodeAsListObject("templating"))
        .apply(c)
  }
}
