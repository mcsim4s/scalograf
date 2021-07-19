package scalograf

import io.circe.Json
import io.circe.Json._

object JsonAnalyzer {

  def diff(leftJson: Json, rightJson: Json): Seq[JsonDiff] = diff(leftJson, rightJson, "root")

  private def diff(leftJson: Json, rightJson: Json, path: String): Seq[JsonDiff] = {
    (leftJson, rightJson) match {
      case (left, right) if left.isObject && right.isObject => Seq.empty
      case (left, right) if left.isArray && right.isArray   => Seq.empty
      case (left, right) if left.isString && right.isString =>
        valueDiff(path, left.asString.get, right.asString.get)
      case (left, right) if left.isNumber && right.isNumber =>
        valueDiff(path, left.asNumber.get, right.asNumber.get)
      case (left, right) if left.isBoolean && right.isBoolean =>
        valueDiff(path, left.asBoolean.get, right.asBoolean.get)
      case (left, right) if left.isNull && right.isNull => Seq.empty
      case (left, right)                                => Seq(TypeDiff(path, left.name, right.name))
    }
  }

  private def valueDiff[T](path: String, left: T, right: T): scala.Seq[JsonDiff] = {
    if (left != right) Seq(ValueDiff(path, left, right)) else Seq.empty
  }

  sealed trait JsonDiff {
    def path: String
  }

  case class TypeDiff(path: String, leftType: String, rightType: String) extends JsonDiff
  case class ValueDiff[T](path: String, leftValue: T, rightValue: T) extends JsonDiff
}
