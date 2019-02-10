package com.touchpay.persistence.dao

import com.touchpay.domain.Credential
import com.touchpay.persistence.json.Find
import io.vertx.core.json.JsonObject
import javax.inject.Inject

class CredentialDao @Inject constructor(private val database: Database) {
    fun getCredentialByPrincipal(principal: JsonObject) = database.genericFindOne<Credential>("credential", Find {
        "_id" to principal.getString("sub")
    }).map {
        it.orElseThrow()
    }

    fun register(credential: Credential) = database.create("credential", credential)

    fun getByUsername(username: String) = database.genericFindOne<Credential>("credential", Find {
        "login" to username
    })
}