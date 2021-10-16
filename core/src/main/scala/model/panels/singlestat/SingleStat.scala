package scalograf
package model.panels.singlestat

import model.panels.Panel
import model.{Color, Target}
import model.time._

import io.circe._
import io.circe.generic.extras.Configuration
import io.circe.generic.extras.semiauto.deriveConfiguredCodec
import scalograf.model.link.Link
import scalograf.model.panels.config.{Config, Mappings}

import scala.concurrent.duration.FiniteDuration

case class SingleStat(
    cacheTimeout: Option[FiniteDuration] = None,
    colorBackground: Boolean = false,
    colorValue: Boolean = false,
    colors: List[Color] = List.empty,
    decimals: Option[Int] = None,
    fieldConfig: Config[SingleStatConfig] = Config[SingleStatConfig](),
    format: String,
    gauge: Gauge,
    hideTimeOverride: Boolean = false,
    interval: Option[String] = None,
    links: List[Link] = List.empty,
    mappingType: Int, //ToDo what's that?
    mappingTypes: List[MappingType] = List.empty, //ToDo what's that?
    maxDataPoints: Int = 100,
    maxPerRow: Option[Int] = None,
    nullPointMode: String = "connected", //ToDo enum
    nullText: Option[String] = None,
    postfix: String,
    postfixFontSize: String, //ToDo font size model
    prefix: String,
    prefixFontSize: String, //ToDo font size model
    rangeMaps: List[RangeMap] = List.empty,
    sparkline: SparkLine,
    tableColumn: String,
    targets: List[Target] = List.empty,
    thresholds: String,
    transparent: Boolean = false,
    valueFontSize: String, //ToDo font size model
    valueMaps: List[Mappings.ValueMapping] = List.empty,
    valueName: String
) extends Panel.Type {
  override def `type`: String = "singlestat"

  override def asJson: JsonObject = SingleStat.codec.encodeObject(this)
}

object SingleStat {
  implicit val codecConfig: Configuration = Configuration.default.withDefaults
  implicit val codec: Codec.AsObject[SingleStat] = deriveConfiguredCodec[SingleStat]
}
