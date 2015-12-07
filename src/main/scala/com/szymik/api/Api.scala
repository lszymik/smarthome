package com.szymik.api

import akka.http.scaladsl.server.directives.Credentials
import com.szymik.config.MainConfig
import com.szymik.core.{Core, CoreActors}

/**
 * The REST API layer. It exposes the REST services, but does not provide any web server interface.
 *
 * Notice that it requires to be mixed in with ``core.CoreActors``, which provides access
 * to the top-level actors that make up the system.
 */
trait Api extends CoreActors with Core with MainConfig {

  implicit lazy val context = system.dispatcher

  lazy val routes = new StatusEndpoint().route

  //    authenticateBasic(realm = "secure endpoint", secureMediaUserPassAuthenticator) { _ =>
  //      new PublicFileEndpoint(publicFileManagerActor).route ~
  //        new PrivateFileEndpoint(privateFileManagerActor).route
  //    }


  def secureMediaUserPassAuthenticator(credentials: Credentials): Option[String] =
    credentials match {
      case p@Credentials.Provided(id)
        if p.verify(config.getString("basic.authorization.password")) && id.equals(config.getString("basic.authorization.user")) => Some(id)
      case _ => None
    }
}
