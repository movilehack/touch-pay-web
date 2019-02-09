package com.touchpay.persistence.json

import io.vertx.core.json.JsonObject

@JsonMarker
abstract class JsonRootBuilder {
    val json = JsonObject()

    infix fun <T> String.to(value: T) {
        json.putUsingMapper(this, value)
    }

    protected fun <T : JsonRootBuilder> to(key: String, builder:T, init: T.() -> Unit) {
        builder.init()
        if (!builder.json.isEmpty) {
            json.putUsingMapper(key, builder.json)
        }
    }
}
