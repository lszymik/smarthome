package com.szymik.smarthome.core

import com.softwaremill.macwire.tagging.Tagger
import com.szymik.smarthome.core.TemperatureActor.TemperatureReading
import com.szymik.smarthome.core.model.TemperatureRecorded

/**
  * Test for Temperature actor.
  */
class TemperatureActorTest extends BaseActorTest {

  "TemperatureActor" should {

    "record temperature" in {
      // given
      val temperatureActor = system.actorOf(TemperatureActor.props()).taggedWith[TemperatureActorTag]

      // when
      temperatureActor ! TemperatureReading("1", "1", 12.3)

      expectMsg(TemperatureRecorded("1234"))
    }
  }

}
