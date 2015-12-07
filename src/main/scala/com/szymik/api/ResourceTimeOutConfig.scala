package com.szymik.api

import akka.util.Timeout

trait ResourceTimeOutConfig {

  import scala.concurrent.duration._

  implicit def resourceTimeout: Timeout = 10.seconds
}
