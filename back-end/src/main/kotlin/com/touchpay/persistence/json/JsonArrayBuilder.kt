package com.touchpay.persistence.json

import io.vertx.core.json.JsonArray

@JsonMarker
abstract class JsonArrayBuilder {
    val json = JsonArray()

    protected fun add(jsonBuilder: JsonBuilder) = json.add(jsonBuilder.json)
}
