package com.touchpay.dto.zoop

import java.time.LocalDateTime

data class SellerRegisterDto(
        val id : String?,
        val first_name : String?,
        val email : String?,
        val phone_number : String?,
        val taxpayer_id : String?,
        val birthdate : String?,
        val created_at: LocalDateTime?
)