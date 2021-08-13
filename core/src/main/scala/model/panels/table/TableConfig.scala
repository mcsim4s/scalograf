package scalograf
package model.panels.table

import model.panels.config.ColorConfig
import model.panels.config.FieldConfig.CustomFieldConfig

import io.circe.generic.extras.Configuration
import io.circe.generic.extras.semiauto.deriveConfiguredCodec

case class TableConfig(
    align: Option[String] = None, //ToDo enum
    color: Option[ColorConfig] = None,
    displayMode: Option[String] = None, //ToDo enum
    filterable: Option[Boolean] = None,
    width: Option[Int] = None
) extends CustomFieldConfig

object TableConfig {
  implicit val config = Configuration.default.withDefaults
  implicit val codec = deriveConfiguredCodec[TableConfig]
}
