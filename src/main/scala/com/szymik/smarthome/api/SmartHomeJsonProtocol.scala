package com.szymik.smarthome.api

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import akka.http.scaladsl.marshalling._
import akka.http.scaladsl.model._
import com.szymik.smarthome.core.model._
import spray.json._

object SmartHomeJsonProtocol {

  case class ResourceLocation(id: String, link: Uri)

  case class ResourceLink(link: Uri)

}

/**
 * Contains useful JSON formats for Media Service.
 */
trait SmartHomeJsonProtocol extends SprayJsonSupport with DefaultJsonProtocol {

  private object ErrorMessage {

    def apply(statusCode: StatusCode, problem: Problem): (StatusCode, JsValue) = {

      val json = JsObject(
        "status" -> JsNumber(statusCode.intValue()),
        "type" -> JsString(classify(statusCode)),
        "message" -> JsString(problem.getMessage)
      )

      (statusCode, json)
    }

    private def classify(statusCode: StatusCode): String = statusCode match {
      case StatusCodes.Forbidden =>
        "insufficient_permissions"
      case StatusCodes.BadRequest =>
        "bad_payload_syntax"
      case StatusCodes.NotFound =>
        "element_resource_non_existing"
      case StatusCodes.InternalServerError =>
        "internal_service_error"
      case _ =>
        "unknown"
    }
  }

  implicit val statusFormat = jsonFormat1(StatusEntity)

  implicit val temperatureRecorded = jsonFormat1(TemperatureRecorded)

  implicit val uriJsonFormat = new JsonFormat[Uri] {

    override def write(uri: Uri): JsValue = JsString(uri.toString)

    override def read(json: JsValue): Uri = json match {
      case JsString(uri) => Uri(uri)
      case _ => throw new DeserializationException("Deserialization failed")
    }
  }

  implicit val problemMarshaller: ToResponseMarshaller[Problem] = Marshaller.combined {

    case problem: AccessForbidden =>
      ErrorMessage(StatusCodes.Forbidden, problem)

    case problem: BadRequest =>
      ErrorMessage(StatusCodes.BadRequest, problem)

    case problem: ResourceNotFound =>
      ErrorMessage(StatusCodes.NotFound, problem)

    case problem: InternalServiceError =>
      ErrorMessage(StatusCodes.InternalServerError, problem)
  }

}


