package scalograf
package model.panels

import io.circe.generic.extras.Configuration
import io.circe.generic.extras.semiauto._

/**
  * Position of the panel in grafana grid layout mode
  *
  * @param w 1-24 (the width of the dashboard is divided into 24 columns)
  * @param h In grid height units, each represents 30 pixels.
  * @param x The x position, in same unit as w.
  * @param y The y position, in same unit as h.
  */
case class GridPosition(w: Int, h: Int, x: Option[Int] = None, y: Option[Int] = None)

object GridPosition {
  implicit val codecConfig = Configuration.default
  implicit val codec = deriveConfiguredCodec[GridPosition]
}
