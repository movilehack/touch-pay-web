package com.touchpay.dto.Zoop

import com.touchpay.domain.Address

data class BuyerRegisterDto(
        val first_name : String,
        val last_name : String,
        val email : String,
        val phone_number : String,
        val taxpayer_id : String,
        val birthdate : String,
        val description : String,
        val address : Address
)