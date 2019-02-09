package com.touchpay.persistence.json

import io.vertx.core.json.JsonArray

class ArrayExpression : JsonArrayBuilder() {
    operator fun String.not() = json.addUsingMapper(this)

    fun eq(leftExpression: String, rightExpression: String) {
        add(JsonBuilder {
            "\$eq" to JsonArray().add(leftExpression).add(rightExpression)
        })
    }

    fun add(init: Query.() -> Unit) {
        val query = Query()
        query.init()
        json.add(query.json)
    }
}
