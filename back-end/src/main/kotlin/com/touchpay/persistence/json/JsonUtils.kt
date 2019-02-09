package com.touchpay.persistence.json

import com.fasterxml.jackson.databind.ObjectMapper
import io.vertx.core.json.JsonArray
import io.vertx.core.json.JsonObject

internal var jsonObjectMapper: ObjectMapper? = null

fun <T : Any?> JsonObject.putUsingMapper(key: String, value: T) {
    this.put(key, jsonObjectMapper?.convertValue(value, Any::class.java))
}

fun <T : Any?> JsonArray.addUsingMapper(value: T) {
    this.add(jsonObjectMapper?.convertValue(value, Any::class.java))
}
