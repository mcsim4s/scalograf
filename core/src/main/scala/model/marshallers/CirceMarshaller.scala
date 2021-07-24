package scalograf
package model.marshallers

import io.circe.generic.semiauto._
import model._

object CirceMarshaller {
  implicit val templatingCodec = deriveCodec[Templating]
  implicit val annotationCodec = deriveCodec[Annotation]
  implicit val annotationsCodec = deriveCodec[Annotations]
  implicit val panelCodec = deriveCodec[Panel]
  implicit val linkCodec = deriveCodec[Link]
  implicit val timePickerCodec = deriveCodec[TimePicker]
  implicit val timeCodec = deriveCodec[Time]

  implicit val dashboardSnippetCodec = deriveCodec[DashboardSnippet]
}
