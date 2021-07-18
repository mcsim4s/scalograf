package scalograf

import JsonAnalyzer._

import io.circe._
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

import scala.io.Source

class JsonAnalyzerSpec extends AnyWordSpec with Matchers {
  val source = Source.fromURL(getClass.getResource("/dashboard.json"))
  val sample = parser.parse(source.getLines().mkString) match {
    case Left(err)    => sys.error(s"test resources are malforme: $err")
    case Right(value) => value
  }
  source.close()

  "JsonAnalyzer" should {
    "return different type diff on root path" in {
      val diff = JsonAnalyzer.diff(sample, Json.arr())

      diff should contain(TypeDiff("root", "Object", "Array"))
    }
  }
}
