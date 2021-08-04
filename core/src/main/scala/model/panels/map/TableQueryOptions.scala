package scalograf
package model.panels.map

import io.circe.Decoder.Result
import io.circe.generic.extras.Configuration
import io.circe.generic.extras.semiauto.deriveConfiguredCodec
import io.circe.syntax._
import io.circe.{Codec, DecodingFailure, HCursor, JsonObject}

sealed trait TableQueryOptions {
  def queryType: String
}

object TableQueryOptions {
  case class GeohashOptions(
      geohashField: String, //ToDo enum
      latitudeField: String,
      longitudeField: String,
      metricField: String
  ) extends TableQueryOptions {
    override val queryType: String = "geohash"
  }

  implicit val config = Configuration.default.withDefaults
  implicit val geohashOptionsCodec = deriveConfiguredCodec[GeohashOptions]

  implicit val codec = new Codec.AsObject[TableQueryOptions] {
    override def apply(c: HCursor): Result[TableQueryOptions] = {
      c.downField("queryType").as[String] match {
        case Left(err)        => Left(err)
        case Right("geohash") => c.as[GeohashOptions]
        case Right(otherType) => Left(DecodingFailure.apply(s"Unknown table query options type $otherType", c.history))
      }
    }

    override def encodeObject(a: TableQueryOptions): JsonObject =
      a match {
        case g: GeohashOptions => geohashOptionsCodec.encodeObject(g).add("queryType", g.queryType.asJson)
      }
  }
}
