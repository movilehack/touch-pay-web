package com.touchpay.dto

import com.touchpay.serializers.Sensitive

class SignInDto(val login: String, @field:Sensitive val password: String)