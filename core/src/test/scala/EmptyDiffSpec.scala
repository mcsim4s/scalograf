package scalograf

import model.Dashboard

import io.circe.parser._
import io.circe.syntax._
import org.scalatest.OptionValues
import org.scalatest.matchers.should
import org.scalatest.wordspec.AnyWordSpec

import java.io.File
import scala.io.Source

class EmptyDiffSpec extends AnyWordSpec with should.Matchers with OptionValues {
  val testDataDir = new File(getClass.getResource("/testDashboards").getPath)

  "Model diff analyzer" should {
    s"get empty diff for community dashboards" in {
      testDataDir.listFiles().foreach { dashboardFile =>
        val json = parse(Source.fromFile(dashboardFile).getLines().mkString) match {
          case Left(value)  => throw value
          case Right(value) => value
        }
        val dashboardJson = json.asObject.get("json").get
        val dashboard = dashboardJson.as[Dashboard] match {
          case Left(value)  => throw value
          case Right(value) => value
        }
        val diff = JsonAnalyzer.diff(dashboardJson, dashboard.asJson)
        diff.foreach(println)
        diff should be(empty)
      }
    }
  }

}
