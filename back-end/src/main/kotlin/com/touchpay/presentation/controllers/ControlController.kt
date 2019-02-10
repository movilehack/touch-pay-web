package com.touchpay.presentation.controllers

import com.touchpay.business.ControlService
import com.touchpay.dto.BlockDto
import com.touchpay.dto.ChangeLimitDto
import com.touchpay.presentation.body
import com.touchpay.presentation.ok
import com.touchpay.presentation.routers.RouteResult
import io.reactivex.Single
import io.vertx.reactivex.ext.web.RoutingContext
import javax.inject.Inject

class ControlController @Inject constructor(private val service: ControlService) {
    fun changeLimit(input: Single<RoutingContext>): Single<RouteResult> =
        input.body<ChangeLimitDto>().flatMapCompletable(service::changeLimit).toSingleDefault(ok())

    fun block(input: Single<RoutingContext>): Single<RouteResult> =
        input.body<BlockDto>().flatMapCompletable(service::block).toSingleDefault(ok())

    fun unblock(input: Single<RoutingContext>): Single<RouteResult> =
        input.body<BlockDto>().flatMapCompletable(service::unblock).toSingleDefault(ok())
}