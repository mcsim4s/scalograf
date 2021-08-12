package scalograf
package model

import cats.syntax.functor._
import io.circe.syntax._
import io.circe.{Decoder, DecodingFailure, Encoder}

sealed trait Color

object Color {
  case class RGB(red: Short, green: Short, blue: Short) extends Color
  case class RGBa(red: Short, green: Short, blue: Short, alpha: Short = 255) extends Color
  case class Named(name: String) extends Color

  implicit val colorEncoder = Encoder.instance[Color] {
    case RGBa(red, green, blue, alpha) => s"rgba($red, $green, $blue, $alpha)".asJson
    case RGB(red, green, blue)         => f"#$red%02X$green%02X$blue%02X".toUpperCase.asJson
    case Named(name)                   => name.asJson
  }

  private implicit val rgbaDecoder = Decoder.instance[RGBa] { cursor =>
    val regex = "^rgba\\((\\d+),\\s*(\\d+),\\s*(\\d+),\\s*(\\d+)\\)$".r
    cursor.value.asString match {
      case Some(regex(red, green, blue, alpha)) => Right(RGBa(red.toShort, green.toShort, blue.toShort, alpha.toShort))
      case Some(_)                              => Left(DecodingFailure("rgba color string have wrong format", cursor.history))
      case _                                    => Left(DecodingFailure("rgba color must be a string", cursor.history))

    }
  }

  private implicit val hexDecoder = Decoder.instance[RGB] { cursor =>
    val regex = "^#(\\w{2})(\\w{2})(\\w{2})$".r
    cursor.value.asString match {
      case Some(regex(red, green, blue)) =>
        Right(
          RGB(
            Integer.parseInt(red, 16).toShort,
            Integer.parseInt(green, 16).toShort,
            Integer.parseInt(blue, 16).toShort
          )
        )
      case Some(_) => Left(DecodingFailure("rgba color string have wrong format", cursor.history))
      case _       => Left(DecodingFailure("rgba color must be a string", cursor.history))

    }
  }

  implicit val colorDecoder =
    List[Decoder[Color]](
      rgbaDecoder.widen,
      hexDecoder.widen,
      implicitly[Decoder[String]].map(Named.apply).widen
    ).reduce(_ or _)
}
