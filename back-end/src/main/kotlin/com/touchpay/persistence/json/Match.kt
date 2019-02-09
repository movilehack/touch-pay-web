package com.touchpay.persistence.json

class Match : Query() {
    infix fun expr(init: Query.() -> Unit) {
        val query = Query()
        query.init()
        putOp("expr", query.json)
    }
}
