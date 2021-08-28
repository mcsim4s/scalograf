package scalograf
package model.panels.singlestat

import model.panels.Panel
import model.{Color, Link, Target}
import model.time._

import io.circe._
import io.circe.generic.extras.Configuration
import io.circe.generic.extras.semiauto.deriveConfiguredCodec

import scala.concurrent.duration.FiniteDuration

case class SingleStat(
    cacheTimeout: Option[FiniteDuration] = None,
    colorBackground: Boolean = false,
    colors: List[Color] = List.empty,
    colorValue: Boolean = false,
    decimals: Option[Int] = None,
    format: String,
    gauge: Gauge,
    interval: Option[String] = None,
    links: List[Link] = List.empty,
    mappingType: Int, //ToDo what's that?
    mappingTypes: List[MappingType] = List.empty, //ToDo what's that?
    maxDataPoints: Int = 100,
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
    valueMaps: List[ValueMap] = List.empty,
    valueName: String
) extends Panel.Type {
  override def `type`: String = "singlestat"

  override def asJson: JsonObject = SingleStat.codec.encodeObject(this)
}

object SingleStat {
  implicit val codecConfig = Configuration.default.withDefaults
  implicit val codec: Codec.AsObject[SingleStat] = deriveConfiguredCodec[SingleStat]
}
