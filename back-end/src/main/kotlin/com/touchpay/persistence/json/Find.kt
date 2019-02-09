package com.touchpay.persistence.json

import io.vertx.core.json.JsonObject
import io.vertx.ext.mongo.FindOptions

class Find() : Query() {
    val options = FindOptions()

    constructor(init: Find.() -> Unit) : this() {
        init()
    }

    fun limit(value: Int) {
        options.limit = value
    }

    fun skip(value: Int) {
        options.skip = value
    }

    infix fun projection(init: Projection.() -> Unit) {
        val projection = Projection()
        projection.init()
        if (!projection.json.isEmpty) {
            options.fields = projection.json
        }
    }

    infix fun sort(init: Sort.() -> Unit) {
        val sort = Sort()
        sort.init()
        if (!sort.json.isEmpty) {
            options.sort = sort.json
        }
    }

    infix fun text(value: String?) {
        if (value != null) {
            json.putUsingMapper("\$text", JsonObject().put("\$search", value))
        }
    }
}
