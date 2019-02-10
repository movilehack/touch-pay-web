package com.touchpay.dto

import com.touchpay.domain.Address
import com.touchpay.serializers.Sensitive
import java.time.LocalDate

data class RegisterDto(
        val cpf: String,
        val login: String,
        val name: String,
        val address: Address,
        val phone: String,
        val email: String,
        @field:Sensitive val password: String,
        val birthDate: LocalDate
)