package org.bowlerframework.model

/**
 * Marker interface for beans to tell RequestMappers a bean that is allowable for creation on GET and DELETE
 * even when it is not found in a Transformer lookup
 */

trait Transient