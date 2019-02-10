package com.touchpay.domain

import com.touchpay.serializers.Sensitive
import java.time.LocalDate

data class Credential(
    val _id: String?,
    val cpf: String,
    val login: String,
    val name: String,
    val phone: String,
    val email: String,
    val address: Address,
    val enabled: Boolean,
    @field:Sensitive val password: String,
    @field:Sensitive val pin: String,
    val birthDate: LocalDate
)