package scalograf

import io.circe.Json
import io.circe.Json._

object JsonAnalyzer {

  def diff(leftJson: Json, rightJson: Json): Seq[JsonDiff] = diff(leftJson, rightJson, "root")

  private def diff(leftJson: Json, rightJson: Json, path: String): Seq[JsonDiff] = {
    (leftJson, rightJson) match {
      case (left, right) if left.isObject && right.isObject   => Seq.empty
      case (left, right) if left.isArray && right.isArray     => Seq.empty
      case (left, right) if left.isString && right.isString   => Seq.empty
      case (left, right) if left.isNumber && right.isNumber   => Seq.empty
      case (left, right) if left.isBoolean && right.isBoolean => Seq.empty
      case (left, right) if left.isNull && right.isNull       => Seq.empty
      case (left, right)                                      => Seq(TypeDiff(path, left.name, right.name))
    }
  }

  sealed trait JsonDiff {
    def path: String
  }

  case class TypeDiff(path: String, leftType: String, rightType: String) extends JsonDiff
}
