package com.touchpay.presentation.controllers

import com.touchpay.business.RegisterService
import com.touchpay.dto.RegisterDto
import com.touchpay.presentation.body
import com.touchpay.presentation.ok
import com.touchpay.presentation.routers.RouteResult
import io.reactivex.Single
import io.vertx.reactivex.ext.web.RoutingContext
import javax.inject.Inject

class RegisterController @Inject constructor(private val service: RegisterService) {
    fun register(input: Single<RoutingContext>): Single<RouteResult> =
            input.body<RegisterDto>().flatMapCompletable(service::register).toSingleDefault(ok())
}