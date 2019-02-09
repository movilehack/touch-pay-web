package com.touchpay.persistence.json

class Projection : Expression() {
    operator fun String.unaryPlus() {
        json.putUsingMapper(this, true)
    }

    operator fun String.unaryMinus() {
        json.putUsingMapper(this, false)
    }

    infix fun String.to(init: Projection.() -> Unit) = to(this, Projection(), init)
}
