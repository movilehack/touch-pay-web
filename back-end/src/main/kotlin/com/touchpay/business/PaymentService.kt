package com.touchpay.business

import com.touchpay.consumers.ZoopConsumer
import com.touchpay.dto.CashOutDto
import com.touchpay.dto.PayDto
import com.touchpay.dto.zoop.TransferenceDto
import com.touchpay.dto.zoop.TransferenceToBankDto
import io.reactivex.Completable
import javax.inject.Inject

class PaymentService @Inject constructor(private val consumer: ZoopConsumer) {
    fun pay(dto: PayDto) = consumer.createTransference(TransferenceDto(
            payerId = "",
            receiverId = "",
            amount = dto.amout,
            description = dto.deviceId
    )).flatMapCompletable { Completable.complete() }

    fun cashOut(dto: CashOutDto) = consumer.transferToBank(TransferenceToBankDto(
        bankId = "",
        amount = dto.amount,
        statement_descriptor = dto.statement_descriptor,
        description = dto.description
    )).flatMapCompletable { Completable.complete() }
}