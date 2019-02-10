package com.touchpay.business

import com.touchpay.consumers.ZoopConsumer
import com.touchpay.domain.Credential
import com.touchpay.dto.CashOutDto
import com.touchpay.dto.PayDto
import com.touchpay.dto.zoop.TransferenceDto
import com.touchpay.dto.zoop.TransferenceToBankDto
import com.touchpay.exceptions.PinInvalidException
import com.touchpay.persistence.dao.AuthenticationDao
import com.touchpay.persistence.dao.CredentialDao
import org.mindrot.jbcrypt.BCrypt
import javax.inject.Inject

class PaymentService @Inject constructor(private val credentialDao: CredentialDao,
                                         private val authenticationDao: AuthenticationDao,
                                         private val consumer: ZoopConsumer,
                                         private val credential: Credential?) {

    fun pay(dto: PayDto) = authenticationDao.getPinByUsername(dto.username).flatMapCompletable {
        if (!it.isPresent || !BCrypt.checkpw(dto.password, it.get())) {
            throw PinInvalidException()
        }
        credentialDao.getByUsername(dto.username).flatMapCompletable { payer ->
            consumer.createTransference(TransferenceDto(
                payerId = payer.get().zoopId,
                receiverId = credential?.zoopId!!,
                amount = dto.amout,
                description = dto.deviceId
            ))
        }
    }

    fun cashOut(dto: CashOutDto) = consumer.transferToBank(TransferenceToBankDto(
        bankId = "",
        amount = dto.amount,
        statement_descriptor = dto.statement_descriptor,
        description = dto.description
    ))
}