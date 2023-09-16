package scalograf
package model.datasource

import io.circe._
import io.circe.generic.extras.Configuration
import io.circe.generic.extras.semiauto.deriveConfiguredCodec

sealed trait DatasourceId

object DatasourceId {
  case class Plain(value: String) extends DatasourceId
  case class Typed(`type`: String, uid: String) extends DatasourceId

  implicit val codecConfig: Configuration = Configuration.default

  implicit val TypedValueCodec: Codec.AsObject[Typed] = deriveConfiguredCodec[Typed]
  implicit val codec: Codec[DatasourceId] = Codec.from[DatasourceId](
    Decoder.instance[DatasourceId] { cursor =>
      cursor.value.asString match {
        case Some(value) => Right(Plain(value))
        case None        => TypedValueCodec.decodeJson(cursor.value)
      }
    },
    Encoder.instance[DatasourceId] {
      case Plain(value) => Json.fromString(value)
      case typed: Typed => TypedValueCodec(typed)
    }
  )
}
