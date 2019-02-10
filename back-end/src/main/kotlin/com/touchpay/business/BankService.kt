package com.touchpay.business

import com.touchpay.consumers.ZoopConsumer
import com.touchpay.domain.Credential
import com.touchpay.dto.AssociateBankDto
import com.touchpay.dto.zoop.AssociateBankCustomerDto
import com.touchpay.dto.zoop.RegisterBankDto
import com.touchpay.persistence.dao.CredentialDao
import javax.inject.Inject

class BankService @Inject constructor(private val credentialDao: CredentialDao,
                                      private val consumer: ZoopConsumer,
                                      private val credential: Credential?) {
    fun registerBank(dto: RegisterBankDto) = consumer.registerBank(dto)

    fun associateBank(dto: AssociateBankDto) = credentialDao.updateBankId(credential!!._id!!, dto.bankId).concatWith {
        consumer.associateBank(AssociateBankCustomerDto(
                customer = credential._id!!,
                token = dto.bankId
        ))
    }
}