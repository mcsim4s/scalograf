package scalograf
package model.datasource

import io.circe.JsonObject
import io.circe.generic.extras.Configuration
import io.circe.generic.extras.semiauto.deriveConfiguredCodec

case class Datasource(
    id: Option[Int] = None,
    orgId: Option[Int] = None,
    uid: Option[String] = None,
    access: String, //ToDo enum
    url: String,
    password: Option[String] = None,
    user: Option[String] = None,
    basicAuth: Boolean = false,
    isDefault: Boolean = false,
    readOnly: Boolean = false,
    //ToDp Datasource model
    `type`: String,
    jsonData: JsonObject
//    secureJsonData: JsonObject //ToDo what is that???
)

object Datasource {
  implicit val config = Configuration.default.withDefaults
  implicit val codec = deriveConfiguredCodec[Datasource]
}
