package scalograf

import model.Dashboard
import model.panels.{GridPosition, Panel}

object GridLayout {
  private[scalograf] case class FreeSpace(x: Int, length: Int) {
    def fits(pos: GridPosition): Option[Int] = {
      pos.x match {
        case Some(value) if value >= x && length >= pos.w + (value - x) => Some(value)
        case None if length >= pos.w                                    => Some(x)
        case _                                                          => None
      }
    }
  }

  private[scalograf] case class Row(columns: IndexedSeq[Boolean] = IndexedSeq.fill(24)(false)) {
    assert(columns.size == 24)

    lazy val space: List[FreeSpace] = {
      columns.zipWithIndex
        .foldLeft[(Boolean, List[FreeSpace])](false -> List.empty) {
          case ((last, result), (current, idx)) =>
            (last, current) match {
              case (_, true)                        => current -> result
              case (true, false)                    => current -> (FreeSpace(idx, 1) :: result)
              case (false, false) if result.isEmpty => current -> List(FreeSpace(idx, 1))
              case (false, false)                   => current -> (result.head.copy(length = result.head.length + 1) :: result.tail)
            }
        }
        ._2
        .reverse
    }

    def fits(panel: Panel): Option[Int] =
      space.foldLeft[Option[Int]](None) {
        case (res, s) =>
          res match {
            case some @ Some(_) => some
            case None           => s.fits(panel.gridPos)
          }
      }
  }

  private[scalograf] case class Grid(rows: List[GridLayout.Row] = List(Row())) {
    def fit(panel: Panel): (Grid, Panel) = {
      val pos = panel.gridPos
      val (x, y) = rows.zipWithIndex
        .foldLeft[Option[(Int, Int)]](None) {
          case (res, (row, y)) =>
            res match {
              case some @ Some(_) => some
              case None           => row.fits(panel).map(_ -> y)
            }
        }
        .getOrElse(throw new IllegalStateException(s"Grid packaging error. $panel doesn't fit to $this"))
      val newPos = pos.copy(x = Some(x), y = Some(y))

      val rowsWithEnoughHeight = rows ++ List.fill(y + pos.h - rows.size + 1)(Row())
      val withPanel = rowsWithEnoughHeight.zipWithIndex.map {
        case (row, yGrid) =>
          Row(
            columns = row.columns.zipWithIndex.map { case (cell, xGrid) => cell || newPos.contains(xGrid, yGrid) }
          )
      }

      def fill(row: List[Row]): List[Row] = {
        row match {
          case Nil => Nil
          case ::(head, tail) =>
            Row(head.columns.zipWithIndex.map { case (cell, x) => cell || tail.exists(_.columns(x)) }) :: fill(tail)
        }
      }
      val filled = fill(withPanel)

      (Grid(filled), panel.copy(gridPos = newPos))
    }

    override def toString: String = {
      val builder = new StringBuilder
      rows.foreach { row =>
        row.columns.foreach(col => if (col) builder.append("x") else builder.append("o"))
        builder.append("\n")
      }
      builder.toString()
    }
  }

  private case class Acc(grid: Grid, panels: List[Panel]) {
    def fit(panel: Panel): Acc = {
      val (newGrid, newPanel) = grid.fit(panel)
      Acc(newGrid, panels :+ newPanel)
    }
  }

  def unpackPanels(dashboard: Dashboard): Dashboard = {
    val panels = dashboard.panels
      .foldRight(List.empty[Panel]) {
        case (panel, acc) =>
          panel.typed match {
            case row: scalograf.model.panels.row.Row =>
              (panel
                .copy(typed = row.copy(panels = List.empty))
                .withPos(panel.gridPos.copy(w = 24, h = 1)) :: row.panels) ++ acc
            case _ => panel :: acc
          }
      }

    val packed = panels.foldLeft(Acc(Grid(), List.empty)) {
      case (acc, panel) => acc.fit(panel)
    }

    dashboard.copy(panels = packed.panels)
  }

  implicit class RichGridPos(val pos: GridPosition) {
    def contains(x: Int, y: Int): Boolean = {
      pos.x.exists(pX => x >= pX && x < pX + pos.w) && pos.y.exists(pY => y >= pY && y < pY + pos.h)
    }
  }

}
