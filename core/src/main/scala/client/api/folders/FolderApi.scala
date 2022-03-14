package scalograf
package client.api.folders

import client.ErrorResponse
import client.api.folders.CreateFolderRequest._
import model.folder.{Folder, FolderInfo}

import io.circe
import io.circe.Json
import sttp.client3._
import sttp.client3.circe._
import sttp.model.Uri
import sttp.model.Uri.{PathSegmentEncoding, Segment}

trait FolderApi[F[_]] {

  def folderUrl(baseUrl: String): Uri = uri"$baseUrl/api/folders"

  /**
   * Get list of folders
   *
   * e.g. [[https://grafana.com/docs/grafana/latest/http_api/folder/#get-all-folders]]
   * @author vl0ft
   * @return response
   */
  protected def listFolders(
      backend: SttpBackend[F, Any],
      url: String,
      grafanaRequest: RequestT[
        Empty,
        Either[String, String],
        Any
      ]): F[Response[Either[ResponseException[ErrorResponse, circe.Error], List[FolderInfo]]]] = {
    grafanaRequest
      .get(folderUrl(url))
      .response(asJsonEither[ErrorResponse, List[FolderInfo]])
      .send(backend)
  }

  /**
   * Get folder by uid
   *
   * e.g. [[https://grafana.com/docs/grafana/latest/http_api/folder/#get-folder-by-uid]]
   * @param uid uid of folder
   * @author vl0ft
   * @return response
   */
  protected def getByUid(
      backend: SttpBackend[F, Any],
      url: String,
      grafanaRequest: RequestT[
        Empty,
        Either[String, String],
        Any
      ],
      uid: String): F[Response[Either[ResponseException[ErrorResponse, circe.Error], Folder]]] = {

    grafanaRequest
      .get(folderUrl(url).addPathSegment(Segment(uid, PathSegmentEncoding.Standard)))
      .response(asJsonEither[ErrorResponse, Folder])
      .send(backend)
  }

  /**
   * Get folder by id
   *
   * e.g. [[https://grafana.com/docs/grafana/latest/http_api/folder/#get-folder-by-id]]
   * @param id id of folder
   * @author vl0ft
   * @return response
   */
  protected def getById(
                          backend: SttpBackend[F, Any],
                          url: String,
                          grafanaRequest: RequestT[
                            Empty,
                            Either[String, String],
                            Any
                          ],
                          id: Long): F[Response[Either[ResponseException[ErrorResponse, circe.Error], Folder]]] = {

    grafanaRequest
      .get(folderUrl(url)
        .addPathSegment(Segment("id", PathSegmentEncoding.Standard))
        .addPathSegment(Segment(id.toString, PathSegmentEncoding.Standard)))
      .response(asJsonEither[ErrorResponse, Folder])
      .send(backend)
  }

  /**
   * Create folder
   *
   * e.g. [[https://grafana.com/docs/grafana/latest/http_api/folder/#create-folder]]
   * @param request CreateFolderRequest
   * @author vl0ft
   * @return response
   */
  protected def createFolder(
      backend: SttpBackend[F, Any],
      url: String,
      grafanaRequest: RequestT[
        Empty,
        Either[String, String],
        Any
      ],
      request: CreateFolderRequest): F[Response[Either[ResponseException[ErrorResponse, circe.Error], Folder]]] = {

    grafanaRequest
      .post(folderUrl(url))
      .body(request)
      .response(asJsonEither[ErrorResponse, Folder])
      .send(backend)
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
  protected def updateFolder(
      backend: SttpBackend[F, Any],
      url: String,
      grafanaRequest: RequestT[
        Empty,
        Either[String, String],
        Any
      ],
      uid: String,
      request: UpdateFolderRequest): F[Response[Either[ResponseException[ErrorResponse, circe.Error], Folder]]] = {

    grafanaRequest
      .put(folderUrl(url).addPathSegment(Segment(uid, PathSegmentEncoding.Standard)))
      .body(request)
      .response(asJsonEither[ErrorResponse, Folder])
      .send(backend)
  }

  /**
   * Delete folder
   *
   * e.g. [[https://grafana.com/docs/grafana/latest/http_api/folder/#delete-folder]]
   * @param uid current uid of folder
   * @author vl0ft
   * @return response
   */
  protected def deleteFolder(
      backend: SttpBackend[F, Any],
      url: String,
      grafanaRequest: RequestT[
        Empty,
        Either[String, String],
        Any
      ],
      uid: String): F[Response[Either[ResponseException[ErrorResponse, circe.Error], Json]]] = {

    grafanaRequest
      .delete(folderUrl(url).addPathSegment(Segment(uid, PathSegmentEncoding.Standard)))
      .response(asJsonEither[ErrorResponse, Json])
      .send(backend)
  }
}
