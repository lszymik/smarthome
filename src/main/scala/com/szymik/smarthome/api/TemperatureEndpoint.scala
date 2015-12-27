package com.szymik.smarthome.api

import akka.actor.{ActorRef, ActorRefFactory}
import akka.event.slf4j.SLF4JLogging
import akka.http.scaladsl.model.StatusCodes
import akka.pattern._
import com.softwaremill.macwire.tagging.@@
import com.szymik.smarthome.core.TemperatureActor.TemperatureReading
import com.szymik.smarthome.core.TemperatureActorTag
import com.szymik.smarthome.core.model.{TemperatureRecorded, InternalServiceError, OperationSuccess, Problem}

import scala.util.{Failure, Success}

/**
  * Endpoint handling temperature recording and getting from sensors.
  */
class TemperatureEndpoint(temperatureActor: ActorRef @@ TemperatureActorTag)(implicit actorRefFactory: ActorRefFactory)
  extends BaseEndpoint with SmartHomeJsonProtocol with SLF4JLogging {

  val route =
    path("home" / Segment / "temperature" / "sensor" / Segment) { (homeId, sensorId) =>
      post {
        onComplete((temperatureActor ? TemperatureReading(homeId, sensorId, 12.3)).mapTo[Either[Problem, TemperatureRecorded]]) {

          case Success(Right(result)) =>
            complete((StatusCodes.Created, result))

          case Success(Left(problem)) =>
            complete(problem)

          case Failure(err) =>
            val message: String = err.getMessage()
            log.error(message)
            complete(InternalServiceError(message))
        }
      }
    }

}
