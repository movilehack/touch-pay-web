package com.touchpay.presentation.controllers

import com.touchpay.business.BankService
import com.touchpay.dto.zoop.RegisterBankDto
import com.touchpay.presentation.body
import com.touchpay.presentation.ok
import com.touchpay.presentation.routers.RouteResult
import io.reactivex.Single
import io.vertx.reactivex.ext.web.RoutingContext
import javax.inject.Inject

class BankController @Inject constructor(private val service: BankService) {
    fun registerBank(input: Single<RoutingContext>): Single<RouteResult> =
            input.body<RegisterBankDto>().flatMapCompletable(service::registerBank).toSingleDefault(ok())
}