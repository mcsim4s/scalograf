package scalograf
package model.panels.stat

import model._
import model.panels._
import model.panels.config.Config
import model.time._
import model.transformations.Transformation

import io.circe._
import io.circe.generic.extras.Configuration
import io.circe.generic.extras.semiauto.deriveConfiguredCodec
import scalograf.model.link.Link

import scala.concurrent.duration.FiniteDuration

case class Stat(
    cacheTimeout: Option[FiniteDuration] = None,
    fieldConfig: Config[StatConfig] = Config(),
    hideTimeOverride: Boolean = false,
    interval: Option[String] = None,
    links: List[Link] = List.empty,
    maxDataPoints: Int = 300,
    options: Options = Options(),
    pluginVersion: Option[String] = None,
    targets: List[Target] = List.empty,
    timeFrom: Option[FiniteDuration] = None,
    timeShift: Option[FiniteDuration] = None,
    transformations: List[Transformation] = List.empty
) extends Panel.Type {
  override def `type`: String = "stat"

  override def asJson: JsonObject = Stat.codec.encodeObject(this)
}

object Stat {
  implicit val codecConfig = Configuration.default.withDefaults
  implicit val codec: Codec.AsObject[Stat] = deriveConfiguredCodec[Stat]
}
