package scalograf
package client

import sttp.client3._
import sttp.client3.asynchttpclient.future.AsyncHttpClientFutureBackend

case class GrafanaClient(url: String, authToken: String) {
  private val backend = AsyncHttpClientFutureBackend()

  def search() = {
    basicRequest
      .header("Authorization", s"Bearer $authToken")
      .get(
        uri"$url/api/search"
          .addParam("limit", "5000")
      )
      .response(asStringAlways)
      .send(backend)
  }

  def close() = {
    backend.close()
  }
}
