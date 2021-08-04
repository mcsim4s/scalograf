package scalograf
package model

import io.circe.Decoder.Result
import io.circe.generic.extras.Configuration
import io.circe.generic.extras.semiauto.{deriveConfiguredDecoder, deriveConfiguredEncoder}
import io.circe.{Codec, HCursor, Json}
import scalograf.model.panels.Panel

case class Dashboard(
    annotations: List[Annotation] = List.empty,
    description: Option[String] = None, //ToDo option or empty default?
    editable: Boolean = true,
    id: Option[Long] = None, //ToDo Option or adt?
    links: List[Link] = List.empty,
    panels: List[Panel] = List.empty,
    refresh: Boolean = true,
    schemaVersion: Long = 0, //ToDo what is this?
    style: String = "default", //ToDo enum
    tags: List[String] = List.empty,
    templating: List[Template] = List.empty,
    time: Time = Time.default, //ToDo time/duration model
    timepicker: TimePicker = TimePicker.empty, //ToDo default
    timezone: String = "browser", //ToDo enum
    title: String = "",
    uid: Option[String] = None, //ToDo option or adt?
    version: Long = 0 //ToDo ???
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
