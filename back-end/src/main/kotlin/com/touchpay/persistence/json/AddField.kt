package com.touchpay.persistence.json

class AddField : Expression() {
    infix fun String.to(init: Projection.() -> Unit) = to(this, Projection(), init)
}
