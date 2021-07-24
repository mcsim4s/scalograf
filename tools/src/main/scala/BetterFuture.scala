package scalograf

import scala.concurrent.{ExecutionContext, Future}

object BetterFuture {
  implicit class RichFutureEither[E <: Throwable, T](val f: Future[Either[E, T]]) {
    def orDie(implicit executionContext: ExecutionContext): Future[T] = f.map(_.toTry).flatMap(Future.fromTry)
  }

  def fromOption[A](option: Option[A]): Future[A] = {
    option match {
      case Some(value) => Future.successful(value)
      case None        => Future.failed(new NoSuchElementException("empty option"))
    }
  }
}
