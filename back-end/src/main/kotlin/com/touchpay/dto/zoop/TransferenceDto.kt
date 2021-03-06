package com.touchpay.dto.zoop

data class TransferenceDto(
    val payerId: String,
    val receiverId: String,
    val amount: Double,
    val description: String?
)