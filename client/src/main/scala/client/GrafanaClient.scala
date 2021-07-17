package scalograf
package client

import sttp.client3._
import sttp.client3.asynchttpclient.future.AsyncHttpClientFutureBackend
import sttp.client3.circe._
import io.circe.generic.auto._
import model.DashboardSnippet

import io.circe

import scala.concurrent.Future

case class GrafanaClient(url: String, authToken: String) {
  private val backend = AsyncHttpClientFutureBackend()

  def search() = {
    basicRequest
      .header("Authorization", s"Bearer $authToken")
      .get(
        uri"$url/api/search"
          .addParam("limit", "5000")
      )
      .response(asJsonAlways[Seq[DashboardSnippet]])
      .send(backend)
  }

  def

  def close() = {
    backend.close()
  }
}
