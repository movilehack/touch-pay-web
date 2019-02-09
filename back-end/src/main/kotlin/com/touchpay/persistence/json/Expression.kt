package com.touchpay.persistence.json

import io.vertx.core.json.JsonArray

abstract class Expression : JsonRootBuilder(){
    fun and(init: ArrayExpression.() -> Unit) {
        val arrayExpression = ArrayExpression()
        arrayExpression.init()
        json.putUsingMapper("\$and", arrayExpression.json)
    }

    fun or(init: ArrayExpression.() -> Unit) {
        val arrayExpression = ArrayExpression()
        arrayExpression.init()
        json.putUsingMapper("\$or", arrayExpression.json)
    }

    fun setUnion(init: ArrayExpression.() -> Unit) {
        val arrayExpression = ArrayExpression()
        arrayExpression.init()
        json.putUsingMapper("\$setUnion", arrayExpression.json)
    }

    fun arrayElemAt(expression: String, index: Int) = json.putUsingMapper("\$arrayElemAt", JsonArray().add(expression).add(index))

    fun map(input: String, alias: String, inn: String) = json.putUsingMapper("\$map", JsonBuilder {
        "input" to input
        "as" to alias
        "in" to inn
    }.json)

    fun reduce(input: String, initialValue: Any, inInit: Query.() -> Unit) {
        val query = Query()
        query.inInit()
        return json.putUsingMapper("\$reduce", JsonBuilder {
            "input" to input
            "initialValue" to initialValue
            "in" to query.json
        }.json)
    }

    protected fun <T : Any?> putOp(op: String, value: T) {
       json.putUsingMapper("\$$op", value)
    }

    infix fun <T> eq(value: T) {
        this.putOp("eq", value)
    }

    infix fun <T> gt(value: T) {
        this.putOp("gt", value)
    }

    infix fun <T> gte(value: T) {
        this.putOp("gte", value)
    }

    fun <T> into(value: List<T>?) {
        if (value != null) {
            json.putUsingMapper("\$in", value)
        }
    }

    infix fun regex(value: String?) {
        this.putOp("regex", value)
    }

    infix fun <T> lt(value: T) {
        this.putOp("lt", value)
    }

    infix fun <T> lte(value: T) {
        this.putOp("lte", value)
    }

    infix fun <T> ne(value: T) {
        this.putOp("ne", value)
    }

    fun e_ne(leftExpression: String, rightExpression: Any?): JsonBuilder {
        val array = JsonArray().add(leftExpression)
        return JsonBuilder {
            "\$ne" to if(rightExpression == null) array.addNull() else array.add(rightExpression)
        }
    }

    fun exists(field: String, value: Boolean){
        json.putUsingMapper(field, JsonBuilder{
            "\$exists" to value
        }.json)
    }

    fun ifNull(field: String, trueResult: Any, falseResult: Any) {
        json.putUsingMapper(field, JsonBuilder {
            "\$ifNull" to JsonArray().add(trueResult).add(falseResult)
        }.json)
    }

    fun <T> nin(vararg value: T) {
        this.putOp("nin", value)
    }

    fun cond(condition: JsonBuilder, trueResult: Any, falseResult: Any) {
        json.putUsingMapper("\$cond", JsonBuilder{
            "if" to condition.json
            "then" to trueResult
            "else" to falseResult
        }.json)
    }
}
