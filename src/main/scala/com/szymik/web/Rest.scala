package com.szymik.web

import com.szymik.api.Api
import com.szymik.core.{BootedCore, Core, CoreActors}

object Rest extends App with BootedCore with Core with CoreActors with Api
