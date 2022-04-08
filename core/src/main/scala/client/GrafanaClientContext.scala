package scalograf
package client

import sttp.client3.{Empty, RequestT, SttpBackend}

case class GrafanaClientContext[F[_]](
    url: String,
    grafanaRequest: RequestT[Empty, Either[String, String], Any],
    backend: SttpBackend[F, Any]
)

object GrafanaClientContext {

  def apply[F[_]](
      url: String,
      grafanaRequest: RequestT[Empty, Either[String, String], Any],
      backend: SttpBackend[F, Any]
  ): GrafanaClientContext[F] = {
    new GrafanaClientContext(url, grafanaRequest, backend)
  }
}
