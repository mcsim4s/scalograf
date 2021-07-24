package scalograf
package client

case class GrafanaConfig(endpoint: Endpoint, auth: Auth)

case class Endpoint(scheme: String, host: String, port: Long)

case class Auth(login: String, password: String)
