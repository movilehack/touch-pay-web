package com.touchpay.business

import com.touchpay.domain.Credential
import com.touchpay.domain.CredentialKind
import com.touchpay.domain.Permission
import com.touchpay.domain.exceptions.NotAuthorizedException
import com.touchpay.persistence.dao.CredentialDao
import com.touchpay.persistence.json.JsonBuilder
import io.reactivex.Observable
import io.reactivex.Single
import io.vertx.reactivex.ext.auth.jwt.JWTAuth
import javax.inject.Inject

class CredentialService @Inject constructor(private val auth: JWTAuth, private val dao: CredentialDao) {
    fun check(authorization: String, kinds: MutableSet<CredentialKind>, permissions: MutableSet<Permission>): Single<Credential> {
        return auth.rxAuthenticate(JsonBuilder {
            "jwt" to authorization
            "options" to {
                "ignoreExpiration" to true
            }
        }.json).flatMap { user ->
            var observable: Observable<Boolean>? = null
            var single: Single<Boolean>? = null

            kinds.forEach {
                val observableAuthorised = user.rxIsAuthorised(it.toString()).toObservable()
                observable = if (observable != null) observable!!.concatWith(observableAuthorised) else observableAuthorised
            }

            if (observable != null) {
                single = treatAuthorised(observable!!)
                observable = null
            }

            permissions.forEach {
                val observableAuthorised = user.rxIsAuthorised(it.toString()).toObservable()
                observable = if (observable != null) observable!!.concatWith(observableAuthorised) else observableAuthorised
            }

            if (observable != null) {
                val singleAuthorised = treatAuthorised(observable!!)
                single = if (single != null) single.flatMap {
                    if (it) singleAuthorised else Single.just(false)
                } else singleAuthorised
            }

            return@flatMap if (single != null) single.map {
                if (!it) {
                    throw NotAuthorizedException()
                }
                user
            } else Single.just(user)
        }.flatMap {
            dao.getCredentialByPrincipal(it.principal())
        }
    }

    private fun treatAuthorised(observable: Observable<Boolean>) = observable.filter { it }.first(false)
}
