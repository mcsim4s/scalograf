package scalograf
package model

import io.circe.Decoder.Result
import io.circe.generic.extras.Configuration
import io.circe.generic.extras.semiauto.{deriveConfiguredDecoder, deriveConfiguredEncoder}
import io.circe.{Codec, HCursor, Json}

case class Dashboard(
    id: Option[Long] = None,
    uid: Option[String] = None,
    style: String = "default",
    tags: List[String] = List.empty,
    timezone: String = "browser",
    templating: List[Template] = List.empty,
    title: String = "",
    schemaVersion: Long = 0,
    editable: Boolean = true,
    annotations: List[Annotation] = List.empty,
    version: Long = 0,
    links: List[Link] = List.empty,
    panels: List[Panel] = List.empty,
    timepicker: TimePicker = TimePicker.empty,
    time: Time = Time.default
)

object Dashboard {
  implicit val codecConfig = Configuration.default
  implicit val dashboardCodec = new Codec[Dashboard] {
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
