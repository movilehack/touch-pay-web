package com.touchpay.persistence.dao

import com.touchpay.persistence.json.Find
import com.touchpay.persistence.json.JsonBuilder
import javax.inject.Inject

class ControlDao @Inject constructor(private val database: Database) {
    fun changeLimit(id: String, newLimit: Double) = database.update("credential", Find {
        "_id" to id
    }, JsonBuilder {
        "limit" to newLimit
    }.json)

    fun changeBlock(id: String, block: Boolean) = database.update("credential", Find {
        "_id" to id
    }, JsonBuilder {
        "block" to block
    }.json)

    fun changePin(id: String, pin: String) = database.update("credential", Find {
        "_id" to id
    }, JsonBuilder {
        "pin" to pin
    }.json)
}