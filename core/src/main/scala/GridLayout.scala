package scalograf

import model.Dashboard
import model.panels.{GridPosition, Panel}

object GridLayout {
  private[scalograf] case class FreeSpace(x: Int, length: Int) {
    def fits(pos: GridPosition): Boolean = {
      pos.x match {
        case Some(value) => length >= pos.w + (value - x)
        case None        => length >= pos.w
      }
    }

    def split(pos: GridPosition): List[FreeSpace] = {
      if (pos.w == length) {
        List.empty
      } else {
        pos.x match {
          case None                                       => List(copy(x + pos.w))
          case Some(value) if value == x                  => List(copy(x + pos.w))
          case Some(value) if x + length == value + pos.w => List(copy(length = length - pos.w))
          case Some(value) =>
            List(
              copy(length = value - x),
              copy(length = (x + length) - (value + pos.w), x = value + pos.w)
            )
        }
      }
    }
  }

  private[scalograf] case class Row(columns: IndexedSeq[Boolean] = IndexedSeq.fill(24)(false)) {
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
  }

  private case class Grid(rows: List[GridLayout.Row])

  def unpackPanels(dashboard: Dashboard): Dashboard = {
    val panels = dashboard.panels
      .foldRight(List.empty[Panel]) {
        case (panel, acc) =>
          panel.typed match {
            case row: scalograf.model.panels.row.Row =>
              (panel
                .copy(typed = row.copy(panels = List.empty))
                .withPos(panel.gridPos.copy(w = 24, h = 1)) :: row.panels) ++ acc
            case other => panel :: acc
          }
      }

    dashboard
  }
}
