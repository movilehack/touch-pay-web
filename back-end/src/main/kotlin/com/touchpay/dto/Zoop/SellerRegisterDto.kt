package com.touchpay.dto.Zoop

import com.touchpay.domain.Address
import java.time.LocalDateTime

data class SellerRegisterDto(
        val id : String?,
        val status : String?,
        val resource: String?,
        val type: String?,
        val account_balance : Double?,
        val current_balance : Double?,
        val fiscal_responsibility : String?,
        val first_name : String?,
        val last_name : String?,
        val email : String?,
        val phone_number : String?,
        val taxpayer_id : String?,
        val birthdate : String?,
        val statement_descriptor : String?,
        val description : Address?,
        val delinquent : Boolean?,
        val default_debit : String?,
        val default_credit : String?,
        val mcc : String?,
        val metadata: Any?,
        val created_at: LocalDateTime?,
        val updated_at: LocalDateTime?
)