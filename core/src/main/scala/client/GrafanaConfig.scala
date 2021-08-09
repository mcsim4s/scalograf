package scalograf
package client

import GrafanaConfig._

case class GrafanaConfig(endpoint: Endpoint, auth: Auth)

object GrafanaConfig {
  case class Endpoint(scheme: String, host: String, port: Long)

  sealed trait Auth

  case class LoginPassword(login: String, password: String) extends Auth
  case class Token(token: String) extends Auth
}
