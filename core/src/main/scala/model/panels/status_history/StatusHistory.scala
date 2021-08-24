package scalograf
package model.panels.status_history

import model.panels._
import model.panels.config.Config
import model.transformations.Transformation
import model.{Link, Target, Time}

import io.circe._
import io.circe.generic.extras.Configuration
import io.circe.generic.extras.semiauto.deriveConfiguredCodec

case class StatusHistory(
    fieldConfig: Config[StatusHistoryConfig] = Config[StatusHistoryConfig](),
    links: List[Link] = List.empty,
    options: Options = Options(),
    repeat: Option[String] = None, //ToDo repeat model
    showHeader: Boolean = false,
    targets: List[Target] = List.empty,
    transformations: List[Transformation] = List.empty,
    transparent: Boolean = false
) extends Panel.Type {
  override def `type`: String = "status-history"

  override def asJson: JsonObject = StatusHistory.codec.encodeObject(this)
}

object StatusHistory {
  implicit val codecConfig = Configuration.default.withDefaults
  implicit val codec: Codec.AsObject[StatusHistory] = deriveConfiguredCodec[StatusHistory]
}
