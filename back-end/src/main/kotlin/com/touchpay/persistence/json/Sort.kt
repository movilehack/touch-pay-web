package com.touchpay.persistence.json

class Sort : Expression() {
    operator fun String.unaryPlus() {
        json.putUsingMapper(this, 1)
    }

    operator fun String.unaryMinus() {
        json.putUsingMapper(this, -1)
    }

    infix fun String.to(init: Sort.() -> Unit) = to(this, Sort(), init)
}
