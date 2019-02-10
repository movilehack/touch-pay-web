package com.touchpay.dto

import com.touchpay.serializers.Sensitive

data class UsernameDto(val username: String, @field:Sensitive val pin: String)