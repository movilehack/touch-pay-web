package com.touchpay.presentation.controllers

import com.touchpay.business.BankService
import com.touchpay.dto.AssociateBankDto
import com.touchpay.dto.zoop.RegisterBankDto
import com.touchpay.presentation.body
import com.touchpay.presentation.json
import com.touchpay.presentation.ok
import com.touchpay.presentation.routers.RouteResult
import io.reactivex.Single
import io.vertx.reactivex.ext.web.RoutingContext
import javax.inject.Inject

class BankController @Inject constructor(private val service: BankService) {
    fun registerBank(input: Single<RoutingContext>): Single<RouteResult> =
            input.body<RegisterBankDto>().flatMap(service::registerBank).map(::json)

    fun associateBank(input: Single<RoutingContext>) : Single<RouteResult> =
            input.body<AssociateBankDto>().flatMapCompletable(service::associateBank).toSingleDefault(ok())
}