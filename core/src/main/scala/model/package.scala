package scalograf

import io.circe.generic.extras.Configuration

package object model {
  implicit val codecConfig = Configuration.default
}
