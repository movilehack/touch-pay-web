package com.touchpay.business

import com.touchpay.dto.ChangeLimitDto
import io.reactivex.Completable
import javax.inject.Inject

class ControlService @Inject constructor() {
    fun changeLimit(dto: ChangeLimitDto): Completable = Completable.complete()
}