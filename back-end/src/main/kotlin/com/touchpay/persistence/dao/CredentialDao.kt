package com.touchpay.persistence.dao

import com.touchpay.domain.Credential
import com.touchpay.persistence.json.Find
import com.touchpay.persistence.json.JsonBuilder
import io.vertx.core.json.JsonObject
import javax.inject.Inject

class CredentialDao @Inject constructor(private val database: Database) {
    fun getCredentialByPrincipal(principal: JsonObject) = database.genericFindOne<Credential>("credential", Find {
        "_id" to principal.getString("sub")
    }).map {
        it.orElseThrow()
    }

    fun register(credential: Credential) = database.create("credential", credential)

    fun hasByLogin(login: String) = database.count("credential", Find {
        "login" to login
    }).map { it > 0 }

    fun hasByEmail(email: String) = database.count("credential", Find {
        "email" to email
    }).map { it > 0 }

    fun getByUsername(login: String) = database.genericFindOne<Credential>("credential", Find {
        "login" to login
    }).map {
        it.orElseThrow()
    }

    fun updateBankId(id: String, bankId: String) = database.update("credential", Find {
        "_id" to id
    }, JsonBuilder {
        "bankId" to bankId
    }.json)

    fun updateTransferMetadataValue(id: String, value: Double) = database.update("credential", Find {
        "_id" to id
    }, JsonBuilder {
        "transferMetadata.value" to value
    }.json)
}