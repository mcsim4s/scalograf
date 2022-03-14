package scalograf
package client

import client.api.folders.{CreateFolderRequest, FolderApi, UpdateFolderRequest}
import client.notifications.NotificationChannel
import model._
import model.datasource.Datasource
import model.folder.{Folder, FolderInfo}

import io.circe
import io.circe.Json
import sttp.client3._
import sttp.client3.circe._

case class GrafanaClient[F[_]](config: GrafanaConfig, backend: SttpBackend[F, Any]) extends FolderApi[F] {
  val url: String = config.endpoint.url

  private[scalograf] val grafanaRequest: RequestT[Empty, Either[String, String], Any] = {
    config.auth match {
      case GrafanaConfig.LoginPassword(login, password) => basicRequest.auth.basic(login, password)
      case GrafanaConfig.Token(token) => basicRequest.auth.bearer(token)
      case GrafanaConfig.NoAuth => basicRequest
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
      params: Map[
        String,
        String
      ]): F[Response[Either[ResponseException[ErrorResponse, circe.Error], Seq[DashboardSnippet]]]] = {
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
      request: DashboardUploadRequest): F[Response[Either[ResponseException[ErrorResponse, circe.Error], DashboardUploadResponse]]] = {
    grafanaRequest
      .post(uri"$url/api/dashboards/db")
      .body(request)
      .response(asJsonEither[ErrorResponse, DashboardUploadResponse])
      .send(backend)
  }

  /**
   * Get list of folders
   *
   * e.g. [[https://grafana.com/docs/grafana/latest/http_api/folder/#get-all-folders]]
   * @author vl0ft
   * @return response
   */
  def listFolders(): F[Response[Either[ResponseException[ErrorResponse, circe.Error], List[FolderInfo]]]] = {
    listFolders(backend, url, grafanaRequest)
  }

  /**
   * Get folder by uid
   *
   * e.g. [[https://grafana.com/docs/grafana/latest/http_api/folder/#create-folder]]
   * @param uid uid of folder
   * @author vl0ft
   * @return response
   */
  def getByUid(uid: String): F[Response[Either[ResponseException[ErrorResponse, circe.Error], Folder]]] = {
    getByUid(backend, url, grafanaRequest, uid)
  }

  /**
   * Get folder by id
   *
   * e.g. [[https://grafana.com/docs/grafana/latest/http_api/folder/#create-folder]]
   * @param id id of folder
   * @author vl0ft
   * @return response
   */
  def getById(id: Long): F[Response[Either[ResponseException[ErrorResponse, circe.Error], Folder]]] = {
    getById(backend, url, grafanaRequest, id)
  }

  /**
   * Create folder
   *
   * e.g. [[https://grafana.com/docs/grafana/latest/http_api/folder/#create-folder]]
   * @param request CreateFolderRequest
   * @author vl0ft
   * @return response
   */
  def createFolder(
      request: CreateFolderRequest): F[Response[Either[ResponseException[ErrorResponse, circe.Error], Folder]]] = {
    createFolder(backend, url, grafanaRequest, request)
  }

  /**
   * Update folder
   *
   * e.g. [[https://grafana.com/docs/grafana/latest/http_api/folder/#update-folder]]
   * @param uid current uid of folder
   * @param request CreateFolderRequest
   * @author vl0ft
   * @return response
   */
  def updateFolder(
      uid: String,
      request: UpdateFolderRequest): F[Response[Either[ResponseException[ErrorResponse, circe.Error], Folder]]] = {
    updateFolder(backend, url, grafanaRequest, uid, request)
  }

  /**
   * Delete folder
   *
   * e.g. [[https://grafana.com/docs/grafana/latest/http_api/folder/#update-folder]]
   * @param uid current uid of folder
   * @author vl0ft
   * @return response
   */
  def deleteFolder(uid: String): F[Response[Either[ResponseException[ErrorResponse, circe.Error], Json]]] = {
    deleteFolder(backend, url, grafanaRequest, uid)
  }

  def listDataSources(): F[Response[Either[ResponseException[ErrorResponse, circe.Error], Json]]] = {
    grafanaRequest
      .get(uri"$url/api/datasources")
      .response(asJsonEither[ErrorResponse, Json])
      .send(backend)
  }

  def createDatasource(
      datasource: Datasource): F[Response[Either[ResponseException[ErrorResponse, circe.Error], DatasourceCreateResponse]]] = {
    grafanaRequest
      .post(uri"$url/api/datasources")
      .body(datasource)
      .response(asJsonEither[ErrorResponse, DatasourceCreateResponse])
      .send(backend)
  }

  def createNotificationChannel(
      channel: NotificationChannel): F[Response[Either[ResponseException[ErrorResponse, circe.Error], NotificationChannel]]] = {
    grafanaRequest
      .post(uri"$url/api/alert-notifications")
      .body(channel)
      .response(asJsonEither[ErrorResponse, NotificationChannel])
      .send(backend)
  }

  /***
    * https://grafana.com/docs/grafana/latest/http_api/alerting_notification_channels/#get-all-notification-channels-lookup
    */
  def lookupNotificationChannels(
    ): F[Response[Either[ResponseException[ErrorResponse, circe.Error], List[NotificationChannel]]]] = {
    grafanaRequest
      .get(uri"$url/api/alert-notifications/lookup")
      .response(asJsonEither[ErrorResponse, List[NotificationChannel]])
      .send(backend)
  }

  /***
    * https://grafana.com/docs/grafana/latest/http_api/alerting_notification_channels/#get-all-notification-channels
    */
  def listNotificationChannels(
    ): F[Response[Either[ResponseException[ErrorResponse, circe.Error], List[NotificationChannel]]]] = {
    grafanaRequest
      .get(uri"$url/api/alert-notifications")
      .response(asJsonEither[ErrorResponse, List[NotificationChannel]])
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
      id: CommunityDashboardId): F[Response[Either[DeserializationException[circe.Error], Json]]] = {
    grafanaRequest
      .get(uri"$url/api/dashboards/${id.id}/revisions/${id.revision}/download")
      .response(asJsonAlways[Json])
      .send(backend)
  }

  def close(): F[Unit] = {
    backend.close()
  }
}
