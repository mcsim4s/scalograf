package scalograf

import org.scalatest.OptionValues
import org.scalatest.matchers.should
import org.scalatest.wordspec.AnyWordSpec
import scalograf.GridLayout.FreeSpace

class GridLayoutSpec extends AnyWordSpec with should.Matchers with OptionValues {
  "Grid.Row" should {
    "return 1 free full space" in {
      GridLayout.Row().space should equal(List(FreeSpace(0, 24)))
    }

    "return 1 free space" in {
      val row = GridLayout.Row(IndexedSeq.fill(10)(true) ++ IndexedSeq.fill(10)(false) ++ IndexedSeq.fill(4)(true))
      row.space should equal(List(FreeSpace(10, 10)))
    }

    "return 2 free space" in {
      val row = GridLayout.Row(IndexedSeq.fill(10)(false) ++ IndexedSeq.fill(10)(true) ++ IndexedSeq.fill(4)(false))
      row.space should equal(List(FreeSpace(0, 10), FreeSpace(20, 4)))
    }

    "return no free space" in {
      val row = GridLayout.Row(IndexedSeq.fill(24)(true))
      row.space should be(empty)
    }
  }
}
