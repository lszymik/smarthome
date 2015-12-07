package com.szymik.core

import akka.util.Timeout

/**
 * Trait that defines default timeout for asynchronous operations.
 */
trait ActorTimeout {

  import scala.concurrent.duration._

  implicit def timeout: Timeout = 10.second
}
