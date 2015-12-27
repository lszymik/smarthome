package com.szymik.smarthome.api

import akka.http.scaladsl.testkit.ScalatestRouteTest
import org.scalatest.{MustMatchers, WordSpecLike}

trait BaseEndpointTest extends WordSpecLike with MustMatchers with ScalatestRouteTest
