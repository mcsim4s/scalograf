package scalograf
package model.time

import scala.concurrent.duration._

sealed trait Time

object Time {
  case object now extends Time {
    def -(duration: FiniteDuration): Relative = Relative(duration)
  }
  case class Absolute(time: java.time.Instant) extends Time
  case class Relative(offset: FiniteDuration) extends Time
}
