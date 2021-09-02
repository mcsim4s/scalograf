package scalograf
package client

import client.GrafanaConfig._

import scala.language.implicitConversions

case class GrafanaConfig(endpoint: Endpoint, auth: Auth)

object GrafanaConfig {
  sealed trait Endpoint {
    def url: String
  }

  case class Url(url: String) extends Endpoint

  case class Scheme(scheme: String, host: String, port: Long) extends Endpoint {
    def url: String = s"$scheme://$host:$port"
  }

  implicit def string2url(url: String): Url = Url(url)

  sealed trait Auth

  case class LoginPassword(login: String, password: String) extends Auth
  case class Token(token: String) extends Auth
  case object NoAuth extends Auth
}
