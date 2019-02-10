package com.touchpay.dto.zoop

data class BuyerRegisterDto(
        val first_name : String?,
        val last_name : String?,
        val email : String?,
        val phone_number : String?,
        val taxpayer_id : String?,
        val birthdate : String?,
        val description : String?,
        val address : AddressDto?
)