package scalograf
package model

import model.time.Time._

import cats.syntax.functor._
import io.circe.syntax._
import io.circe.{Decoder, DecodingFailure, Encoder}

import scala.concurrent.duration.{DAYS, FiniteDuration, HOURS, MINUTES, SECONDS}
import scala.util.{Failure, Success, Try}

package object time {
  val now: Time.now.type = Time.now

  implicit val relativeDecoder: Decoder[Relative] = Decoder.instance[Relative] { cursor =>
    val regex = "^now\\s*-\\s*(\\d+)([dhms])$".r
    cursor.value.asString match {
      case Some(regex(len, unitString)) =>
        val temporalUnit = unitString match {
          case "s" => SECONDS
          case "m" => MINUTES
          case "h" => HOURS
          case _   => DAYS
        }
        Right(Relative(FiniteDuration(len.toLong, temporalUnit)))
      case Some(_) => Left(DecodingFailure("relative time string have wrong format", cursor.history))
      case _       => Left(DecodingFailure("relative time must be a string", cursor.history))

    }
  }

  implicit val nowDecoder: Decoder[now.type] = Decoder.instance[now.type] { cursor =>
    cursor.value.asString match {
      case Some("now") => Right(now)
      case Some(_)     => Left(DecodingFailure("now string have wrong format", cursor.history))
      case _           => Left(DecodingFailure("now must be a string", cursor.history))

    }
  }

  implicit val absoluteDecoder: Decoder[Absolute] = Decoder.instance[Absolute] { cursor =>
    cursor.value.asString match {
      case Some(value) =>
        Try(java.time.Instant.parse(value)) match {
          case Success(time) => Right(Absolute(time))
          case Failure(exception) =>
            Left(DecodingFailure(s"absolute time string have wrong format: ${exception.getMessage}", cursor.history))
        }
      case _ => Left(DecodingFailure("now must be a string", cursor.history))
    }
  }

  implicit val durationEncoder: Encoder[FiniteDuration] = Encoder.instance[FiniteDuration] { duration =>
    val unit = duration.unit match {
      case DAYS    => "d"
      case HOURS   => "h"
      case MINUTES => "m"
      case _       => "s"
    }
    s"${duration.length}$unit".asJson
  }

  implicit val durationDecoder: Decoder[FiniteDuration] = Decoder.instance[FiniteDuration] { cursor =>
    val regex = "^(\\d+)([dhms])$".r
    cursor.value.asString match {
      case Some(regex(len, unitString)) =>
        val temporalUnit = unitString match {
          case "s" => SECONDS
          case "m" => MINUTES
          case "h" => HOURS
          case _   => DAYS
        }
        Right(FiniteDuration(len.toLong, temporalUnit))
      case Some(_) => Left(DecodingFailure("Duration string have wrong format", cursor.history))
      case _       => Left(DecodingFailure("Duration must be a string", cursor.history))

    }
  }

  implicit val encoder: Encoder[Time] = Encoder.instance[Time] {
    case Absolute(time)   => time.toString.asJson
    case Relative(offset) => s"now-${offset.asJson(durationEncoder).asString.get}".asJson
    case _                => "now".asJson
  }

  implicit val decoder: Decoder[Time] =
    List[Decoder[Time]](
      relativeDecoder.widen,
      nowDecoder.widen,
      absoluteDecoder.widen
    ).reduce(_ or _)

}
