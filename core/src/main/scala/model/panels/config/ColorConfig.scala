package scalograf
package model.panels.config

import model.Color
import model.enums.ColorMode

import io.circe.generic.extras.Configuration
import io.circe.generic.extras.semiauto.deriveConfiguredCodec

case class ColorConfig(
    mode: ColorMode = ColorMode.PaletteClassic,
    fixedColor: Option[Color] = None
)
object ColorConfig {

  implicit val codecConfig = Configuration.default
  implicit val codec = deriveConfiguredCodec[ColorConfig]
}
