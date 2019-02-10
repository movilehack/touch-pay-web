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
import io.reactivex.Single
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

        credentialDao.getByUsername(dto.login).flatMap { payer ->
            if (payer.transferMetadata.date == LocalDate.now() && (payer.transferMetadata.value + dto.value) > payer.limit) {
                throw LimitException()
            } else if (payer.transferMetadata.date != LocalDate.now()) {
                credentialDao.updateTransferMetadataValue(payer._id!!, 0.0).toSingleDefault(payer)
            }
            else Single.just(payer)
        }.flatMapCompletable { payer ->
            consumer.createTransference(TransferenceDto(
                    payerId = payer.zoopId,
                    receiverId = credential?.zoopId!!,
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