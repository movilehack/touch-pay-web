package com.touchpay.dto

import com.touchpay.serializers.Sensitive

data class SignInDto(val email: String, @field:Sensitive val password: String)