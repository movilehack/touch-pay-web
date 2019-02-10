package com.touchpay.presentation.controllers

import com.touchpay.business.AuthenticationService
import com.touchpay.dto.SignInDto
import com.touchpay.presentation.body
import com.touchpay.presentation.json
import com.touchpay.presentation.routers.RouteResult
import io.reactivex.Single
import io.vertx.reactivex.ext.web.RoutingContext
import javax.inject.Inject

class AuthenticationController @Inject constructor(private val service: AuthenticationService) {
    fun signIn(input: Single<RoutingContext>): Single<RouteResult> =
            input.body<SignInDto>().flatMap(service::signIn).map(::json)
}
