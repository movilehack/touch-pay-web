package com.touchpay.persistence.json

class GroupExpression : JsonRootBuilder() {
    fun first(expression: String) = json.putUsingMapper("\$first", expression)
    fun push(expression: String) = json.putUsingMapper("\$push", expression)
    fun push(init: Query.() -> Unit) {
        val query = Query()
        query.init()
        json.putUsingMapper("\$push", query.json)
    }
}
