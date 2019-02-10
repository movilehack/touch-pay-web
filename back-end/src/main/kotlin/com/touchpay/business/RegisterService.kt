package com.touchpay.business

import com.touchpay.dto.RegisterDto
import io.reactivex.Completable
import javax.inject.Inject

class RegisterService @Inject constructor() {
    fun register(dto: RegisterDto): Completable = Completable.complete()
}