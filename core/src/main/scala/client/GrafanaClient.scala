package scalograf
package client

import model._
import model.datasource.Datasource

import io.circe
import io.circe.Json
import sttp.client3._
import sttp.client3.circe._

case class GrafanaClient[F[_]](config: GrafanaConfig, backend: SttpBackend[F, Any]) {
  val url: String = config.endpoint.url

  private[scalograf] val grafanaRequest = {
    config.auth match {
      case GrafanaConfig.LoginPassword(login, password) => basicRequest.auth.basic(login, password)
      case GrafanaConfig.Token(token)                   => basicRequest.auth.bearer(token)
      case GrafanaConfig.NoAuth                         => basicRequest
    }
  }

  /**
   * Allow to add query parameters for a request.
   *
   * e.g. [[https://grafana.com/docs/grafana/latest/http_api/folder_dashboard_search/]]
   * @author vl0ft
   * @param params map of query parameters
   * @return response
   */
  def search(
      params: Map[String, String])
  : F[Response[Either[ResponseException[ErrorResponse, circe.Error], Seq[DashboardSnippet]]]] = {
    grafanaRequest
      .get(
        params
          .foldLeft(uri"$url/api/search")((url, params) => url.addParam(params._1, params._2))
      )
      .response(asJsonEither[ErrorResponse, Seq[DashboardSnippet]])
      .send(backend)
  }

  def getDashboard(uid: String): F[Response[Either[ResponseException[ErrorResponse, circe.Error], Dashboard]]] = {
    dashboardInner(asJsonEither[ErrorResponse, Dashboard])(uid)
  }

  def uploadDashboard(
      request: DashboardUploadRequest
  ): F[Response[Either[ResponseException[ErrorResponse, circe.Error], DashboardUploadResponse]]] = {
    grafanaRequest
      .post(uri"$url/api/dashboards/db")
      .body(request)
      .response(asJsonEither[ErrorResponse, DashboardUploadResponse])
      .send(backend)
  }

  def listDataSources(): F[Response[Either[ResponseException[ErrorResponse, circe.Error], Json]]] = {
    grafanaRequest
      .get(uri"$url/api/datasources")
      .response(asJsonEither[ErrorResponse, Json])
      .send(backend)
  }

  def createDatasource(
      datasource: Datasource
  ): F[Response[Either[ResponseException[ErrorResponse, circe.Error], DatasourceCreateResponse]]] = {
    grafanaRequest
      .post(uri"$url/api/datasources")
      .body(datasource)
      .response(asJsonEither[ErrorResponse, DatasourceCreateResponse])
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
      .get(uri"$url/api/dashboards/${id.id}/revisions/${id.revision}/download")
      .response(asJsonAlways[Json])
      .send(backend)
  }

  def close(): F[Unit] = {
    backend.close()
  }
}
