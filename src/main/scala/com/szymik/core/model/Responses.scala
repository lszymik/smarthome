package com.szymik.core.model

/**
 * Link to media on storage service.
 *
 * @param uri temporary to media on storage service.
 */
case class GetFileResponse(uri: String)

/**
 * Returns user metadata
 *
 * @param metadata provided by user.
 */
case class GetFileMetadataResponse(metadata: String)

/**
 * Indicates that file has been successfully deleted.
 */
case object FileDeleted
