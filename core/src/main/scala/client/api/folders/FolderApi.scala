package scalograf
package client.api.folders

import client.api.folders.CreateFolderRequest._
import client.{ErrorResponse, GrafanaClientContext}
import model.folder.{Folder, FolderInfo}

import io.circe
import io.circe.Json
import sttp.client3._
import sttp.client3.circe._
import sttp.model.Uri
import sttp.model.Uri.{PathSegmentEncoding, Segment}

trait FolderApi[F[_]] {

  protected def clientContext(): GrafanaClientContext[F]

  private def folderUrl(ctx: GrafanaClientContext[F]): Uri = uri"${ctx.url}/api/folders"

  /**
    * Get list of folders
    *
    * e.g. [[https://grafana.com/docs/grafana/latest/http_api/folder/#get-all-folders]]
    * @author vl0ft
    * @return response
    */
  def listFolders(): F[Response[Either[ResponseException[ErrorResponse, circe.Error], List[FolderInfo]]]] = {
    val ctx = clientContext()

    ctx.grafanaRequest
      .get(folderUrl(ctx))
      .response(asJsonEither[ErrorResponse, List[FolderInfo]])
      .send(ctx.backend)
  }

  /**
    * Get folder by uid
    *
    * e.g. [[https://grafana.com/docs/grafana/latest/http_api/folder/#get-folder-by-uid]]
    * @param uid uid of folder
    * @author vl0ft
    * @return response
    */
  def getByUid(uid: String): F[Response[Either[ResponseException[ErrorResponse, circe.Error], Folder]]] = {
    val ctx = clientContext()

    ctx.grafanaRequest
      .get(folderUrl(ctx).addPathSegment(Segment(uid, PathSegmentEncoding.Standard)))
      .response(asJsonEither[ErrorResponse, Folder])
      .send(ctx.backend)
  }

  /**
    * Get folder by id
    *
    * e.g. [[https://grafana.com/docs/grafana/latest/http_api/folder/#get-folder-by-id]]
    * @param id id of folder
    * @author vl0ft
    * @return response
    */
  def getById(id: Long): F[Response[Either[ResponseException[ErrorResponse, circe.Error], Folder]]] = {
    val ctx = clientContext()

    ctx.grafanaRequest
      .get(
        folderUrl(ctx)
          .addPathSegment(Segment("id", PathSegmentEncoding.Standard))
          .addPathSegment(Segment(id.toString, PathSegmentEncoding.Standard))
      )
      .response(asJsonEither[ErrorResponse, Folder])
      .send(ctx.backend)
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
      request: CreateFolderRequest
  ): F[Response[Either[ResponseException[ErrorResponse, circe.Error], Folder]]] = {
    val ctx = clientContext()

    ctx.grafanaRequest
      .post(folderUrl(ctx))
      .body(request)
      .response(asJsonEither[ErrorResponse, Folder])
      .send(ctx.backend)
  }

  /**
    * Update folder
    *
    * e.g. [[https://grafana.com/docs/grafana/latest/http_api/folder/#update-folder]]
    * @param uid current uid of folder
    * @param request UpdateFolderRequest
    * @author vl0ft
    * @return response
    */
  def updateFolder(
      uid: String,
      request: UpdateFolderRequest
  ): F[Response[Either[ResponseException[ErrorResponse, circe.Error], Folder]]] = {
    val ctx = clientContext()

    ctx.grafanaRequest
      .put(folderUrl(ctx).addPathSegment(Segment(uid, PathSegmentEncoding.Standard)))
      .body(request)
      .response(asJsonEither[ErrorResponse, Folder])
      .send(ctx.backend)
  }

  /**
    * Delete folder
    *
    * e.g. [[https://grafana.com/docs/grafana/latest/http_api/folder/#delete-folder]]
    * @param uid current uid of folder
    * @author vl0ft
    * @return response
    */
  def deleteFolder(uid: String): F[Response[Either[ResponseException[ErrorResponse, circe.Error], Json]]] = {
    val ctx = clientContext()

    ctx.grafanaRequest
      .delete(folderUrl(ctx).addPathSegment(Segment(uid, PathSegmentEncoding.Standard)))
      .response(asJsonEither[ErrorResponse, Json])
      .send(ctx.backend)
  }
}
