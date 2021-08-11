package scalograf
package model

import model.Refresh.Never
import model.annotations.Annotation
import model.panels.Panel

import io.circe.generic.extras.Configuration
import io.circe.generic.extras.semiauto.{deriveConfiguredDecoder, deriveConfiguredEncoder}

case class Dashboard(
    __inputs: List[Input] = List.empty,
    __requires: List[Requirement] = List.empty,
    annotations: List[Annotation] = List.empty,
    description: Option[String] = None,
    editable: Boolean = true,
    gnetId: Option[Long] = None,
    graphTooltip: Option[Long] = None,
    id: Option[Long] = None, //ToDo Option or adt?
    iteration: Option[Long] = None,
    links: List[Link] = List.empty,
    panels: List[Panel] = List.empty,
    refresh: Refresh = Never,
    schemaVersion: Option[Long] = None,
    style: String = "default", //ToDo enum
    tags: List[String] = List.empty,
    templating: List[Template] = List.empty,
    time: TimeRange = TimeRange("now - 1h", "now - 1m"), //ToDo time/duration model
    timepicker: TimePicker = TimePicker(),
    timezone: String = "browser", //ToDo enum
    title: String,
    uid: Option[String] = None, //ToDo option or adt?
    version: Option[Long] = None
)

object Dashboard {
  implicit val codecConfig = Configuration.default

  implicit val encoder = deriveConfiguredEncoder[Dashboard]
    .mapJsonObject(encodeAsListObject("annotations"))
    .mapJsonObject(encodeAsListObject("templating"))

  implicit val decoder = deriveConfiguredDecoder[Dashboard]
    .prepare(decodeAsListObject("annotations"))
    .prepare(decodeAsListObject("templating"))
}
