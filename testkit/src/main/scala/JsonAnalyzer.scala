package scalograf

import io.circe.{Json, JsonObject}

import scala.util.matching.Regex

object JsonAnalyzer {
  private val ignores: Seq[Regex] = Seq(
    // those are actually fields of the 'table-old' panel in new grafana
    "\\[table\\]\\.columns".r,
    "\\[table\\]\\.styles".r,
    "\\[table\\]\\.transform".r,
    //Grafana 7 deprecated
    "templating\\.list\\[\\w+\\]\\.tagsQuery".r,
    "templating\\.list\\[\\w+\\]\\.tagValuesQuery".r,
    "templating\\.list\\[\\w+\\]\\.useTags".r,
    "templating\\.list\\[\\w+\\]\\.error".r,
    "templating\\.list\\[\\w+\\]\\.queryValue".r,
    "templating\\.list\\[\\w+\\]\\.skipUrlSync".r,
    "templating\\.list\\[datasource\\]\\.refresh".r,
    "templating\\.list\\[query\\]\\.query".r,

    // Auto generated from alert, and doesn't work for some reason. use fieldconfig.thresholds
    "panels\\[timeseries\\]\\.thresholds".r,

    // idk what it is, so just ignore that for now
    "list\\[adhoc\\]\\.filters\\[\\d+\\].condition".r,
    "panels\\[timeseries\\]\\.fieldConfig\\.defaults\\.custom\\.hideFrom".r,
  )

  def diff(leftJson: Json, rightJson: Json): Seq[JsonDiff] =
    diff(leftJson, rightJson, "root")
      .filterNot(d => ignores.exists(r => r.findFirstIn(d.path).nonEmpty))

  private def diff(leftJson: Json, rightJson: Json, path: String): Seq[JsonDiff] = {
    (leftJson, rightJson) match {
      case (left, right) if left.isObject && right.isObject =>
        objectDiff(path, left.asObject.get, right.asObject.get)
      case (left, right) if left.isArray && right.isArray =>
        val leftArray = if(path.endsWith("properties")) left.asArray.get.sortBy(_.asObject.get("id").toString) else left.asArray.get
        val rightArray = if(path.endsWith("properties")) right.asArray.get.sortBy(_.asObject.get("id").toString) else right.asArray.get
        arrayDiff(path, leftArray, rightArray)
      case (left, right) if left.isString && right.isString =>
        val leftVal = left.asString.map(s => if (s.startsWith("#")) s.toUpperCase else s).get
        val rightVal = right.asString.map(s => if (s.startsWith("#")) s.toUpperCase else s).get
        valueDiff(path, leftVal, rightVal)
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
          case None    => Seq(FieldMissing(s"$path.$key", l))
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
    if (left != right)
      Seq(ValueDiff(path, left, right))
    else
      Seq.empty
  }

  sealed trait JsonDiff {
    def path: String
  }

  case class TypeDiff(path: String, leftType: String, rightType: String) extends JsonDiff
  case class ValueDiff[T](path: String, leftValue: T, rightValue: T) extends JsonDiff
  case class FieldMissing(path: String, value: Json) extends JsonDiff {
    override def toString: String = s"Field Missing: \"$path\": ${value.name} : ${value.noSpaces}"
  }
  case class ExtraField[T](path: String, fieldName: String, value: Json) extends JsonDiff {
    override def toString: String = s"Extra field: \"$path.$fieldName\": ${value.name} : ${value.noSpaces}"
  }
}
