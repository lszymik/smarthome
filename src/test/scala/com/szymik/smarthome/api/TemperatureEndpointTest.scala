package com.szymik.smarthome.api

import akka.actor.ActorDSL._
import akka.http.scaladsl.model.StatusCodes
import com.szymik.smarthome.core.TemperatureActor.TemperatureReading
import com.szymik.smarthome.core.TemperatureActorTag
import com.szymik.smarthome.core.model.TemperatureRecorded

import com.softwaremill.macwire.tagging.Tagger

/**
  * Test for temperature endpoint.
  */
class TemperatureEndpointTest extends BaseEndpointTest with SmartHomeJsonProtocol {

  "TemperatureEndpoint" should {

    "record temperature" in {
      // given
      val temperatureActor = actor(new Act {
        become {
          case TemperatureReading("1", "1", value) =>
            sender() ! Right(TemperatureRecorded("1"))
        }
      }).taggedWith[TemperatureActorTag]

      val temperatureEndpoint = new TemperatureEndpoint(temperatureActor)

      // when
      Post("/home/1/temperature/sensor/1") ~> temperatureEndpoint.route ~> check {
        // then
        status mustBe StatusCodes.Created
        responseAs[TemperatureRecorded] mustBe TemperatureRecorded("1")
      }

    }

  }

}
