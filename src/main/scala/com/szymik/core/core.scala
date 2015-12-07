package com.szymik.core

import akka.actor.{ActorRefFactory, ActorSystem}
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer
import com.softwaremill.macwire.tagging.Tagger
import com.szymik.api.Api
import com.szymik.config.MainConfig
import com.typesafe.scalalogging.LazyLogging

import scala.util.{Failure, Success}

/**
 * Core is type containing the ``system: ActorSystem`` member. This enables us to use it in our
 * apps as well as in our tests.
 */
trait Core {

  protected implicit val system: ActorSystem
}

/**
 * This trait implements ``Core`` by starting the required ``ActorSystem`` and registering the
 * termination handler to stop the system when the JVM exits.
 */
trait BootedCore extends Core with Api with MainConfig {

  protected lazy implicit val system = ActorSystem("smarthome")

  protected lazy implicit val materializer = ActorMaterializer()

  protected def actorRefFactory: ActorRefFactory = system

  private lazy val port = config.getInt("server.port")

  Http().bindAndHandle(routes, "0.0.0.0", port) onComplete {

    // Ensure that the constructed ActorSystem and HTTP server are shut down when the JVM shuts down
    case Success(binding) =>
      logger.info(s"Server started, port: $port")
      sys.addShutdownHook {
        binding.unbind()
        system.terminate()
        logger.info("Server stopped")
      }

    case Failure(e) =>
      logger.error("Cannot start server")
      sys.addShutdownHook {
        system.terminate()
        logger.info("Server stopped")
      }
  }
}

/**
 * This trait contains the actors that make up our application; it can be mixed in with
 * ``BootedCore`` for running code or ``TestKit`` for unit and integration tests.
 */
trait CoreActors extends LazyLogging {
  this: Core with MainConfig =>

}
