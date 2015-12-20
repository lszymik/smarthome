package com.szymik.smarthome.web

import com.szymik.smarthome.api.Api
import com.szymik.smarthome.core.{CoreActors, Core, BootedCore}

object Rest extends App with BootedCore with Core with CoreActors with Api
