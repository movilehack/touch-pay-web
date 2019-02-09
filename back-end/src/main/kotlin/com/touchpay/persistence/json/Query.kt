package com.touchpay.persistence.json

open class Query : Expression() {
    infix fun String.to(init: Query.() -> Unit) = to(this, Query(), init)
}
