package com.touchpay.persistence.dao

import com.touchpay.persistence.json.Pipeline
import io.reactivex.Single
import java.util.*
import javax.inject.Inject

class AuthenticationDao @Inject constructor(private val database: Database) {
    fun getCredentialByEmail(email: String): Single<Optional<String>>
            = database.aggregateOne("credential", Pipeline {
        match {
            "email" to email
            "enabled" to true
        }

        projection {
            -"_id"
            +"password"
        }
    }).map {
        if (it.isPresent) Optional.of(it.get().getString("password"))
        else Optional.empty()
    }

    fun getPinByUsername(username: String): Single<Optional<String>>
        = database.aggregateOne("credential", Pipeline {
        match {
            "username" to username
            "enabled" to true
        }

        projection {
            -"_id"
            +"pin"
        }
    }).map {
        if (it.isPresent) Optional.of(it.get().getString("pin"))
        else Optional.empty()
    }
}
