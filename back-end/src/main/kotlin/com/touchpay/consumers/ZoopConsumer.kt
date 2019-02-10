package com.touchpay.consumers

import com.touchpay.dto.zoop.*
import java.time.LocalDate
import javax.inject.Inject
import javax.inject.Named

class ZoopConsumer @Inject constructor(@Named("zoop") private val consume: ConsumeApi){

    private data class CreateSellerOutputDto
    (
            val id :	String,
            val status : String,
            val resource : String,
            val account_balance: Double,
            val current_balance	 : Double,
            val fiscal_responsibility: String?,
            val first_name : String,
            val last_name : String?,
            val email : String,
            val phone_number : String,
            val taxpayer_id : String,
            val birthdate : String,
            val statement_descriptor: String?,
            val description : String?,
            val Address : Address?,
            val delinquent : Boolean?,
            val default_debit : String?,
            val default_credit : String?,
            val mcc: String?,
            val metadata : Any?,
            val created_at : String?,
            val updated_at : String?
    )

    private data class Address
    (
            val line1: String,
            val line2: String,
            val line3: String,
            val neighborhood: String,
            val city: String,
            val state: String,
            val postal_code: String,
            val country_code: String
    )

    private data class Transference
    (
            val amount: Double,
            val description: String?,
            val transfer_date: String
    )

    private data class TransferenceToBank
    (
            val amount : Double,
            val statement_descriptor : String?,
            val description : String?
    )

    private data class RegisterBankOutputDto
    (
            val id: String,
            val resource: String,
            val holder_name: String,
            val taxpayer_id: String,
            val description: String?,
            val bank_name: String,
            val bank_code: String,
            val type: String,
            val last4_digits: String,
            val account_number: Long,
            val country_code: String,
            val routing_number: Long,
            val phone_number: String?,
            val is_active: Boolean,
            val is_verified: Boolean,
            val debitable: Boolean,
            val customer: String?,
            val fingerprint: String?,
            val address: Address?,
            val verification_checklist: VerificationChecklist,
            val metadata: Any?,
            val uri: String,
            val created_at: String,
            val updated_at: String
    )

    private data class VerificationChecklist
    (
        val postal_code_check : String,
        val address_line1_check: String,
        val deposit_check: String
    )

    fun createSeller(dto: SellerRegisterDto) = consume.post<CreateSellerOutputDto>("/sellers/individuals", dto).map {
        it.id
    }

    fun associateBank(dto: AssociateBankCustomerDto) = consume.post("/bank_account", dto)

    fun registerBank(dto: RegisterBankDto) = consume.post<RegisterBankOutputDto>("bank_accounts/tokens", dto).map {
        it.id
    }

    fun createTransference(dto: TransferenceDto) = consume.post("/transfers/${dto.payerId}/to/${dto.receiverId}", Transference(
            amount = dto.amount,
            description = dto.description,
            transfer_date = LocalDate.now().toString()
    ))

    fun transferToBank(dto: TransferenceToBankDto) = consume.post("/bank_accounts/${dto.bankId}/transfers", TransferenceToBank(
            amount  = dto.amount,
            statement_descriptor = dto.statement_descriptor,
            description = dto.description
    ))
}