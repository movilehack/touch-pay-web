package com.touchpay.dto

import com.touchpay.serializers.Sensitive

class SignInDto(val email: String, @field:Sensitive val password: String)