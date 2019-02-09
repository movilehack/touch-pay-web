package com.touchpay.consumers

import com.touchpay.dto.Zoop.BuyerRegisterDto
import com.touchpay.dto.Zoop.Output.BuyerRegisterOutputDto
import com.touchpay.dto.Zoop.Output.SellerRegisterOutputDto
import com.touchpay.dto.Zoop.SellerRegisterDto
import com.touchpay.dto.Zoop.TransferenceDto
import java.time.LocalDate
import javax.inject.Inject
import javax.inject.Named

class ZoopConsumer @Inject constructor(@Named("zoop") private val consume: ConsumeApi){

    private data class createSellerOutputDto
    (
            val id :	String,
            val status : String,
            val resource : String,
            val account_balance: Int,
            val current_balance	 : Int,
            val fiscal_responsibility: String,
            val first_name : String,
            val last_name : String,
            val email : String,
            val phone_number : String,
            val taxpayer_id : String,
            val birthdate : String,
            val statement_descriptor: String,
            val description : String,
            val Address : Address,
            val delinquent : Boolean,
            val default_debit : String,
            val default_credit : String,
            val mcc: String,
            val metadata : Any,
            val created_at : String,
            val updated_at : String
    )

    private data class createBuyerOutputDto
    (
            val id :	String,
            val status : String,
            val resource : String,
            val account_balance: Int,
            val current_balance	 : Int,
            val first_name : String,
            val last_name : String,
            val email : String,
            val phone_number : String,
            val taxpayer_id : String,
            val birthdate : String,
            val description : String,
            val Address : Address,
            val delinquent : Boolean,
            val default_debit : String,
            val default_credit : String,
            val metadata : Any,
            val created_at : String,
            val updated_at : String
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

    fun createBuyer(dto: BuyerRegisterDto) = consume.post<createBuyerOutputDto>("/buyers", dto).map {
        BuyerRegisterOutputDto(
                id = it.id
        )
    }

    fun createSeller(dto: SellerRegisterDto) = consume.post<createSellerOutputDto>("/sellers/individuals", dto).map {
        SellerRegisterOutputDto(
                id = it.id
        )
    }

    fun createTransference(dto: TransferenceDto) = consume.post("/transfers/" + dto.payerId + "/to/" + dto.receiverId, Transference(
            amount = dto.amount,
            description = dto.description,
            transfer_date = LocalDate.now().toString()
    ))
}