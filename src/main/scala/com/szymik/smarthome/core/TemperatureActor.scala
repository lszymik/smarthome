package com.szymik.smarthome.core

import akka.actor.{Props, Actor, ActorLogging}
import com.szymik.smarthome.core.TemperatureActor.TemperatureReading
import com.szymik.smarthome.core.model.TemperatureRecorded

trait TemperatureActorTag

/**
  * Communication protocol with temperature actor.
  *
  */
object TemperatureActor {

  /**
    * Record new temperature reading.
    *
    * @param homeId id of home where measurement was done.
    * @param sensorId id of sensor.
    * @param value temperature value.
    */
  case class TemperatureReading(homeId: String, sensorId: String, value: Double)

  /**
    * Creates temperature record.
    *
    * @return Props for creating new actor.
    */
  def props() = Props(new TemperatureActor())
}

/**
  * Created by lszymik on 26/12/15.
  */
class TemperatureActor extends Actor with ActorLogging with ActorTimeout {

  override def receive: Receive = {

    case TemperatureReading(homeId, sensorId, value) =>
      log.debug(s"Temperature recorded in home '$homeId' from sensor '$sensorId' value '$value'")
      sender() ! TemperatureRecorded("1234")
  }

}
