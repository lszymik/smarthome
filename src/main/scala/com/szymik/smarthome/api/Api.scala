package com.szymik.smarthome.api

import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.directives.Credentials
import com.szymik.smarthome.config.MainConfig
import com.szymik.smarthome.core.{Core, CoreActors}

/**
 * The REST API layer. It exposes the REST services, but does not provide any web server interface.
 *
 * Notice that it requires to be mixed in with ``core.CoreActors``, which provides access
 * to the top-level actors that make up the system.
 */
trait Api extends CoreActors with Core with MainConfig {

  implicit lazy val context = system.dispatcher

  lazy val routes =
    authenticateBasic(realm = "secure endpoint", secureMediaUserPassAuthenticator) { _ =>
      new StatusEndpoint().route ~
        new TemperatureEndpoint(temperatureActor).route
    }

  def secureMediaUserPassAuthenticator(credentials: Credentials): Option[String] =
    credentials match {
      case p@Credentials.Provided(id)
        if p.verify(config.getString("basic.authorization.password")) && id.equals(config.getString("basic.authorization.user")) => Some(id)
      case _ => None
    }
}
