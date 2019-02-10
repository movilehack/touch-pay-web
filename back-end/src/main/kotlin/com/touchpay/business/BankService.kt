package com.touchpay.business

import com.touchpay.consumers.ZoopConsumer
import com.touchpay.dto.zoop.RegisterBankDto
import io.reactivex.Completable
import javax.inject.Inject

class BankService @Inject constructor(private val consumer: ZoopConsumer) {
    fun registerBank(dto: RegisterBankDto) = consumer.registerBank(dto).flatMapCompletable {
        Completable.complete()
    }
}