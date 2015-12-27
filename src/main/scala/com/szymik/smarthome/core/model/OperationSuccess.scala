package com.szymik.smarthome.core.model

/**
  * Marker trait for all generic success operation.
  */
sealed trait OperationSuccess;

/**
  * Successfully recorded temperature from sensor.
  * @param id of created record.
  */
case class TemperatureRecorded(id: String) extends OperationSuccess

