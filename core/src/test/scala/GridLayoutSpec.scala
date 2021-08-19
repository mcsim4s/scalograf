package scalograf

import org.scalatest.OptionValues
import org.scalatest.matchers.should
import org.scalatest.wordspec.AnyWordSpec
import scalograf.GridLayout.FreeSpace

class GridLayoutSpec extends AnyWordSpec with should.Matchers with OptionValues {
  "Grid.Row" should {
    "return 1 free space" in {
      GridLayout.Row().space should equal(List(FreeSpace(0, 24)))
    }
  }
}
