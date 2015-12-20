package com.szymik.smarthome.config

import com.typesafe.config.ConfigFactory

trait MainConfig {

  lazy val config = ConfigFactory.load()
}
