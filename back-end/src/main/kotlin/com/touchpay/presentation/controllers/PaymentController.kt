package com.touchpay.presentation.controllers

import com.touchpay.business.PaymentService
import com.touchpay.dto.CashOutDto
import com.touchpay.dto.PayDto
import com.touchpay.presentation.body
import com.touchpay.presentation.ok
import com.touchpay.presentation.routers.RouteResult
import io.reactivex.Single
import io.vertx.reactivex.ext.web.RoutingContext
import javax.inject.Inject

class PaymentController @Inject constructor(private val service: PaymentService){
    fun pay(input: Single<RoutingContext>): Single<RouteResult> =
            input.body<PayDto>().flatMapCompletable(service::pay).toSingleDefault(ok())

    fun cashOut(input: Single<RoutingContext>): Single<RouteResult> =
            input.body<CashOutDto>().flatMapCompletable(service::cashOut).toSingleDefault(ok())

    fun cashIn(input: Single<RoutingContext>): Single<RouteResult> =
            input.body()
}