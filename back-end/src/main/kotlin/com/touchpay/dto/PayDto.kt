package com.touchpay.dto

data class PayDto(
    val username: String,
    val password: String,
    val amout: Double,
    val deviceId: String
)