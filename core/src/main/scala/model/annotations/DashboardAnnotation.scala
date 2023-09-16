package scalograf
package model.annotations

import model.Color

import io.circe.JsonObject
import io.circe.generic.extras.Configuration
import io.circe.generic.extras.semiauto.deriveConfiguredCodec
import model.datasource.DatasourceId

case class DashboardAnnotation(
    `type`: String,
    builtIn: Int = 0,
    datasource: DatasourceId,
    enable: Boolean = true,
    hide: Boolean = false,
    iconColor: Color,
    name: String,
    target: Option[AnnotationTarget] = None
) extends Annotation {
  override def asJson: JsonObject = DashboardAnnotation.codec.encodeObject(this)
}

object DashboardAnnotation {
  implicit val codecConfig = Configuration.default.withDefaults
  implicit val codec = deriveConfiguredCodec[DashboardAnnotation]
}
