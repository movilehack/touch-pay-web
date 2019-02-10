package com.touchpay.dto

data class PayDto(
        val login: String,
        val pin: String,
        val value: Double,
        val deviceId: String
)