package com.touchpay.persistence.json

class JsonBuilder() : JsonRootBuilder() {

    constructor(init: JsonBuilder.() -> Unit) : this() {
        init()
    }

    infix fun String.to(init: JsonBuilder.() -> Unit) = to(this, JsonBuilder(), init)
}
