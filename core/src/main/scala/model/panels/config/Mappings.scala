package scalograf
package model.panels.config

import model.Color

import cats.syntax.functor._
import io.circe.Decoder.Result
import io.circe.generic.extras.Configuration
import io.circe.generic.extras.semiauto._
import io.circe.syntax._
import io.circe.{Codec, Decoder, HCursor, Json}

sealed trait Mappings

object Mappings {
  case class Mapping(index: Int, text: String, color: Option[Color] = None)
  case class OptionsMapping(options: Map[String, Mapping], `type`: String) extends Mappings

  case class ValueMapping(
      id: Option[Int] = None,
      op: String,
      text: String,
      value: String,
      `type`: Option[Int] = None
  ) extends Mappings

  implicit val config: Configuration = Configuration.default.withDefaults

  implicit val mappingCodec: Codec.AsObject[Mapping] = deriveConfiguredCodec[Mapping]
  implicit val optionsMappingCodec: Codec.AsObject[OptionsMapping] = deriveConfiguredCodec[OptionsMapping]
  implicit val valueMappingCodec: Codec.AsObject[ValueMapping] = deriveConfiguredCodec[ValueMapping]

  implicit val codec: Codec[Mappings] = new Codec[Mappings] {
    override def apply(a: Mappings): Json =
      a match {
        case o: OptionsMapping => o.asJson
        case v: ValueMapping   => v.asJson
      }

    private val decoder = List[Decoder[Mappings]](
      Decoder[ValueMapping].widen,
      Decoder[OptionsMapping].widen
    ).reduceLeft(_ or _)

    override def apply(c: HCursor): Result[Mappings] = decoder.apply(c)
  }
}
