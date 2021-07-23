package scalograf
package model

case class Dashboard(
    id: Long,
    uid: String,
    style: String,
    tags: List[String],
    timezone: String,
    templating: Templating,
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
