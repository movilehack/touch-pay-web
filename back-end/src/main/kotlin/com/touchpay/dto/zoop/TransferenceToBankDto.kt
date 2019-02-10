package com.touchpay.dto.zoop

data class TransferenceToBankDto(
    val bankId: String,
    val amount : Double,
    val statement_descriptor : String?,
    val description : String?
)