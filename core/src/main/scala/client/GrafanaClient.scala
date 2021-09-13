package scalograf
package client

import model._
import model.datasource.Datasource

import io.circe
import io.circe.Json
import sttp.client3._
import sttp.client3.circe._

case class GrafanaClient[F[_]](config: GrafanaConfig, private val backend: SttpBackend[F, Any]) {
  private val url: String = config.endpoint.url

  private val grafanaRequest = {
    config.auth match {
      case GrafanaConfig.LoginPassword(login, password) => basicRequest.auth.basic(login, password)
      case GrafanaConfig.Token(token)                   => basicRequest.auth.bearer(token)
    }
  }

  def search(): F[Response[Either[ResponseException[Json, circe.Error], Seq[DashboardSnippet]]]] = {
    grafanaRequest
      .get(
        uri"$url/api/search"
          .addParam("limit", "5000")
      )
      .response(asJsonEither[Json, Seq[DashboardSnippet]])
      .send(backend)
  }

  def getDashboard(uid: String): F[Response[Either[ResponseException[Json, circe.Error], Dashboard]]] = {
    dashboardInner(asJsonEither[Json, Dashboard])(uid)
  }

  def uploadDashboard(
      request: DashboardUploadRequest
  ): F[Response[Either[ResponseException[Json, circe.Error], Unit]]] = {
    grafanaRequest
      .post(uri"$url/api/dashboards/db")
      .body(request)
      .response(asJsonEither[Json, Unit])
      .send(backend)
  }

  def listDataSources(): F[Response[Either[ResponseException[Json, circe.Error], Json]]] = {
    grafanaRequest
      .get(uri"$url/api/datasources")
      .response(asJsonEither[Json, Json])
      .send(backend)
  }

  def createDatasource(datasource: Datasource): F[Response[Either[ResponseException[Json, circe.Error], Unit]]] = {
    grafanaRequest
      .post(uri"$url/api/datasources")
      .body(datasource)
      .response(asJsonEither[Json, Unit])
      .send(backend)
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
