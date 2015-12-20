package com.szymik.smarthome.core.model

/**
 * Parent class for all failed request results.
 *
 * @param message describes the cause of the failure
 */
sealed abstract class Problem(message: String) {
  def getMessage: String = message
}

/**
 * Resource not found.
 *
 * @param message details about problem
 */
case class ResourceNotFound(message: String) extends Problem(message)

/**
 * Internal server error.
 *
 * @param message details about problem
 */
case class InternalServiceError(message: String) extends Problem(message)

/**
 * Bad request
 *
 * @param message with root cause of bad request.
 */
case class BadRequest(message: String) extends Problem(message)


/**
 * Access to media file forbidden.
 *
 * @param message describes the cause of the failure.
 */
case class AccessForbidden(message: String) extends Problem(message)
