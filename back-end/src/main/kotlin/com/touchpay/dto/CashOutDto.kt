package com.touchpay.dto

data class CashOutDto(
    val amount: Double,
    val statement_descriptor : String?,
    val description : String?
)