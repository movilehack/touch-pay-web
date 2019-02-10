package com.touchpay.business

import com.touchpay.dto.SignInDto
import io.reactivex.Single
import javax.inject.Inject

class AuthenticationService @Inject constructor() {
    fun signIn(dto: SignInDto): Single<String> = Single.just("")
}