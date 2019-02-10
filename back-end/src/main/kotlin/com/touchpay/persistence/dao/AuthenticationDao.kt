package com.touchpay.persistence.dao

import com.touchpay.dto.output.CredentialOutputDto
import com.touchpay.persistence.json.Pipeline
import io.reactivex.Single
import java.util.*
import javax.inject.Inject

class AuthenticationDao @Inject constructor(private val database: Database) {
    fun getCredentialByEmail(email: String): Single<Optional<CredentialOutputDto>>
            = database.genericAggregateOne("credential", Pipeline {
        match {
            "email" to email
            "enabled" to true
        }

        projection {
            +"password"
        }
    })

    fun getPinByUsername(username: String): Single<Optional<String>>
        = database.aggregateOne("credential", Pipeline {
        match {
            "login" to username
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
