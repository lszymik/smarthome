package com.szymik.smarthome.api

import akka.http.scaladsl.testkit.ScalatestRouteTest
import org.scalatest.{MustMatchers, WordSpecLike}

trait BaseResourceTest extends WordSpecLike with MustMatchers with ScalatestRouteTest
