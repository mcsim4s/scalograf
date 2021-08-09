package scalograf
package client

import model._

import io.circe
import io.circe.Json
import sttp.client3._
import sttp.client3.asynchttpclient.future.AsyncHttpClientFutureBackend
import sttp.client3.circe._

import scala.concurrent.Future

case class GrafanaClient[F[_]](config: GrafanaConfig, backend: SttpBackend[F, Any]) {

  private val url = {
    import config.endpoint._
    s"$scheme://$host:$port"
  }

  private val grafanaRequest = {
    config.auth match {
      case GrafanaConfig.LoginPassword(login, password) => basicRequest.auth.basic(login, password)
      case GrafanaConfig.Token(token)                   => basicRequest.auth.bearer(token)
    }
  }

  def search(): F[Response[Either[DeserializationException[circe.Error], Seq[DashboardSnippet]]]] = {
    grafanaRequest
      .get(
        uri"$url/api/search"
          .addParam("limit", "5000")
      )
      .response(asJsonAlways[Seq[DashboardSnippet]])
      .send(backend)
  }

  def dashboard(uid: String): F[Response[Either[DeserializationException[circe.Error], Dashboard]]] = {
    dashboardInner(asJsonAlways[Dashboard])(uid)
  }

  private[scalograf] def dashboardRaw(uid: String): F[Response[Either[DeserializationException[circe.Error], Json]]] = {
    dashboardInner(asJsonAlways[Json])(uid)
  }

  private def dashboardInner[T](responseAs: ResponseAs[T, Any])(uid: String): F[Response[T]] = {
    grafanaRequest
      .get(uri"$url/api/dashboards/uid/$uid")
      .response(responseAs)
      .send(backend)
  }

  private[scalograf] def importJson(
      id: CommunityDashboardId
  ): F[Response[Either[DeserializationException[circe.Error], Json]]] = {
    grafanaRequest
      .get(uri"$url/api/gnet/dashboards/${id.id}")
      .response(asJsonAlways[Json])
      .send(backend)
  }

  def close(): F[Unit] = {
    backend.close()
  }
}
