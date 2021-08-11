package scalograf
package model.panels.table

import model.panels.FieldConfig.CustomFieldConfig

import io.circe.generic.extras.Configuration
import io.circe.generic.extras.semiauto.deriveConfiguredCodec

case class TableConfig(
    align: Option[String] = None, //ToDo enum
    displayMode: String = "auto", //ToDo enum
    filterable: Boolean = false
) extends CustomFieldConfig

object TableConfig {
  implicit val config = Configuration.default.withDefaults
  implicit val codec = deriveConfiguredCodec[TableConfig]
}
