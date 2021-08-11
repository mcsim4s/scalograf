package scalograf
package model.panels.singlestat

import model.panels.{GridPosition, Panel}
import model.{Color, Link, Target, Time}

import io.circe._
import io.circe.generic.extras.Configuration
import io.circe.generic.extras.semiauto.deriveConfiguredCodec

case class SingleStat(
    cacheTimeout: Option[Time] = None,
    colorBackground: Boolean = false,
    colors: List[Color] = List.empty,
    colorValue: Boolean = false,
    datasource: Option[String] = None,
    decimals: Option[Double] = None,
    description: Option[String] = None,
    format: String,
    gauge: Gauge,
    gridPos: GridPosition,
    id: Option[Int] = None,
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
    title: Option[String] = None,
    transparent: Boolean = false,
    valueFontSize: String, //ToDo font size model
    valueMaps: List[ValueMap] = List.empty,
    valueName: String
) extends Panel {
  override def `type`: String = "singlestat"

  override def asJson: JsonObject = SingleStat.codec.encodeObject(this)
}

object SingleStat {
  implicit val codecConfig = Configuration.default.withDefaults
  implicit val codec: Codec.AsObject[SingleStat] = deriveConfiguredCodec[SingleStat]
}
