package com.szymik.api

import akka.event.slf4j.SLF4JLogging
import com.szymik.core.model.StatusEntity

import scala.concurrent.ExecutionContext

/**
 * Simple endpoint returning just ping indicating that microservice is alive.
 *
 */
class StatusEndpoint(implicit executionContext: ExecutionContext) extends BaseEndpoint with SLF4JLogging {

  val route =
    path("status") {
      get {
        complete(StatusEntity("ok"))
      }
    }
}
