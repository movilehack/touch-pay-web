package com.touchpay.business

import com.touchpay.consumers.ZoopConsumer
import com.touchpay.domain.Credential
import com.touchpay.dto.CashOutDto
import com.touchpay.dto.PayDto
import com.touchpay.dto.zoop.TransferenceDto
import com.touchpay.dto.zoop.TransferenceToBankDto
import com.touchpay.exceptions.LimitException
import com.touchpay.exceptions.PinInvalidException
import com.touchpay.persistence.dao.AuthenticationDao
import com.touchpay.persistence.dao.CredentialDao
import io.reactivex.Completable
import org.mindrot.jbcrypt.BCrypt
import java.time.LocalDate
import javax.inject.Inject

class PaymentService @Inject constructor(private val credentialDao: CredentialDao,
                                         private val authenticationDao: AuthenticationDao,
                                         private val consumer: ZoopConsumer,
                                         private val credential: Credential?) {

    fun pay(dto: PayDto) = authenticationDao.getPinByUsername(dto.login).flatMapCompletable {
        if (!it.isPresent || !BCrypt.checkpw(dto.pin, it.get())) {
            throw PinInvalidException()
        }

        if (credential!!.transferMetadata.date == LocalDate.now() && (credential.transferMetadata.value + dto.value) > credential.limit) {
            throw LimitException()
        }

        credentialDao.getByUsername(dto.login).flatMapCompletable { payer ->
            consumer.createTransference(TransferenceDto(
                payerId = payer.get().zoopId,
                receiverId = credential.zoopId,
                amount = dto.value,
                description = dto.deviceId
            ))
        }
    }

    fun cashOut(dto: CashOutDto) = consumer.transferToBank(TransferenceToBankDto(
        bankId = credential!!.bankId!!,
        amount = dto.amount,
        statement_descriptor = dto.statement_descriptor,
        description = dto.description
    ))

    fun cashIn() = Completable.complete() //Endpoint não disponibilizado pela api da zoop no hackaton
}