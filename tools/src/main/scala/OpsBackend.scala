package scalograf

import org.slf4j.LoggerFactory
import sttp.capabilities
import sttp.client3.asynchttpclient.future.AsyncHttpClientFutureBackend
import sttp.client3.{Request, Response, SttpBackend}
import sttp.monad.MonadError

import scala.concurrent.{ExecutionContext, Future}

case class OpsBackend()(implicit ec: ExecutionContext) extends SttpBackend[Future, Any] {
  private val log = LoggerFactory.getLogger(this.getClass)

  private final val delegate = AsyncHttpClientFutureBackend()

  override def send[T, R >: Any with capabilities.Effect[Future]](request: Request[T, R]): Future[Response[T]] = {
    logRequest(request)
    val p = for {
      result <- delegate.send(request)
      _ = logResponse(result)
    } yield result
    p.failed.foreach(e => log.error("Request error", e))
    p
  }

  private def logRequest(request: Request[_, _]): Unit = {
    log.info(s"Making request to ${request.uri}. With body: ${request.body}")
  }

  private def logResponse[T](resp: Response[T]): Unit = {
    log.info(s"Response status: ${resp.statusText}. With body: ${resp.body}")
  }

  override def close(): Future[Unit] = delegate.close()

  override def responseMonad: MonadError[Future] = delegate.responseMonad
}
