package com.touchpay.persistence.json

class Group : JsonRootBuilder() {
    infix fun String.to(init: GroupExpression.() -> Unit) = to(this, GroupExpression(), init)
}
