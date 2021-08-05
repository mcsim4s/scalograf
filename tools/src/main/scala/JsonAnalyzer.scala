package scalograf

import io.circe.{Json, JsonObject}
import io.circe.Json._

object JsonAnalyzer {

  def diff(leftJson: Json, rightJson: Json): Seq[JsonDiff] = diff(leftJson, rightJson, "root")

  private def diff(leftJson: Json, rightJson: Json, path: String): Seq[JsonDiff] = {
    (leftJson, rightJson) match {
      case (left, right) if left.isObject && right.isObject =>
        objectDiff(path, left.asObject.get, right.asObject.get)
      case (left, right) if left.isArray && right.isArray =>
        arrayDiff(path, left.asArray.get, right.asArray.get)
      case (left, right) if left.isString && right.isString =>
        valueDiff(path, left.asString.get, right.asString.get)
      case (left, right) if left.isNumber && right.isNumber =>
        valueDiff(path, left.asNumber.get, right.asNumber.get)
      case (left, right) if left.isBoolean && right.isBoolean =>
        valueDiff(path, left.asBoolean.get, right.asBoolean.get)
      case (left, right) if left.isNull && right.isNull =>
        Seq.empty
      case (left, right) =>
        Seq(TypeDiff(path, left.name, right.name))
    }
  }

  def objectDiff(path: String, left: JsonObject, right: JsonObject): Seq[JsonDiff] = {
    val missing = left.toMap.flatMap {
      case (key, l) =>
        right(key) match {
          case Some(r) => diff(l, r, s"$path.$key")
          case None    => Seq(FieldMissing(path, key, l))
        }
    }.toSeq

//    val extra = right.toMap.filter(p => !left.contains(p._1)).map { case (k, v) =>
//      ExtraField(path, k, v)
//    }

    missing
  }

  private def arrayDiff(path: String, left: Vector[Json], right: Vector[Json]): Seq[JsonDiff] = {
    val sizeDiff = if (left.size != right.size) {
      Seq(ValueDiff(s"$path.size", left.size, right.size))
    } else Seq.empty
    sizeDiff ++ (left zip right).zipWithIndex.flatMap { case ((l, r), index) =>
      val idxString = l.asObject.flatMap(_ ("type")).flatMap(_.asString) match {
        case Some(value) => value
        case None => index.toString
      }
      diff(l, r, s"$path[$idxString]")
    }
  }

  private def valueDiff[T](path: String, left: T, right: T): Seq[JsonDiff] = {
    if (left != right) Seq(ValueDiff(path, left, right)) else Seq.empty
  }

  sealed trait JsonDiff {
    def path: String
  }

  case class TypeDiff(path: String, leftType: String, rightType: String) extends JsonDiff
  case class ValueDiff[T](path: String, leftValue: T, rightValue: T) extends JsonDiff
  case class FieldMissing(path: String, fieldName: String, value: Json) extends JsonDiff {
    override def toString: String = s"Field Missing: \"$path.$fieldName\": ${value.name} : ${value.noSpaces}"
  }
  case class ExtraField[T](path: String, fieldName: String, value: Json) extends JsonDiff {
    override def toString: String = s"Extra field: \"$path.$fieldName\": ${value.name} : ${value.noSpaces}"
  }
}
