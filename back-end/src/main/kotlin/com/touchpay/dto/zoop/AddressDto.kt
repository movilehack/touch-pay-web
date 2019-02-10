package com.touchpay.dto.zoop

data class AddressDto(
        val street: String?,
        val neighborhood : String?,
        val city: String?,
        val state: String?,
        val postal_code: String?,
        val country_code: String?
)