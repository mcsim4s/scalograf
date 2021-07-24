package scalograf
package model

import marshallers.CirceMarshaller._
import io.circe.generic.semiauto.deriveCodec

case class Dashboard(
    id: Option[Long],
    uid: String,
    style: String,
    tags: List[String],
    timezone: String,
//    templating: Templating,
    title: String,
    schemaVersion: Long,
    editable: Boolean,
    annotations: Annotations,
    version: Long,
    links: List[Link],
    panels: List[Panel],
//    timePicker: TimePicker,
    time: Time
)

object Dashboard {
  implicit val dashboardCodec = deriveCodec[Dashboard]
}
