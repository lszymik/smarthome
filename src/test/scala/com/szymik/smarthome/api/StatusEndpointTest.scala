package com.szymik.smarthome.api

import akka.http.scaladsl.model.StatusCodes

class StatusEndpointTest extends BaseEndpointTest {

  "StatusEndpoint" should {

    "respond with 200" in {
      //given
      val resource = new StatusEndpoint()

      // when
      Get("/status") ~> resource.route ~> check {

        // then
        status mustBe StatusCodes.OK
      }
    }

  }
}
