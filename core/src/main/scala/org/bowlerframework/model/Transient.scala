package org.bowlerframework.model

/**
 * Marker interface for RequestMappers, designating a bean that is allowable to create on GET and DELETE
 * even when it is not found in a Transformer lookup
 */

trait Transient