package scalograf

import JsonAnalyzer._

import io.circe._
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

import scala.io.Source

class JsonAnalyzerSpec extends AnyWordSpec with Matchers {
  val source = Source.fromURL(getClass.getResource("/dashboard.json"))
  val sample = parser.parse(source.getLines().mkString) match {
    case Left(err)    => sys.error(s"test resources are malformed: $err")
    case Right(value) => value
  }
  source.close()

  "JsonAnalyzer" should {
    "return different type diff on root path" in {
      val diff = JsonAnalyzer.diff(sample, Json.arr())

      diff should contain(TypeDiff("root", "Object", "Array"))
    }
    "return js numbers are differ" in {
      val left = parser.parse("3.4").getOrElse(sys.error("illegal test"))
      val right = parser.parse("3.5").getOrElse(sys.error("illegal test"))
      val diff = JsonAnalyzer.diff(left, right)

      diff should contain(ValueDiff("root", left.asNumber.get, right.asNumber.get))
    }
    "return js booleans are differ" in {
      val left = parser.parse("true").getOrElse(sys.error("illegal test"))
      val right = parser.parse("false").getOrElse(sys.error("illegal test"))
      val diff = JsonAnalyzer.diff(left, right)

      diff should contain(ValueDiff("root", true, false))
    }
    "return js strings are differ" in {
      val left = parser.parse("\"3.4\"").getOrElse(sys.error("illegal test"))
      val right = parser.parse("\"3.5\"").getOrElse(sys.error("illegal test"))
      val diff = JsonAnalyzer.diff(left, right)

      diff should contain(ValueDiff("root", "3.4", "3.5"))
    }
  }
}
