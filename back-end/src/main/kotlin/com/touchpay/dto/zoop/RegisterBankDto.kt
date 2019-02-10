package com.touchpay.dto.zoop

data class RegisterBankDto(
        val holder_name: String,
        val bank_code : Long,
        val routing_number: Int,
        val account_number: Long,
        val taxpayer_id: String,
        val type: String //checking ou savings
)