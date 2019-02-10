package com.touchpay.business

import com.touchpay.consumers.ZoopConsumer
import com.touchpay.domain.Credential
import com.touchpay.domain.TransferMetadata
import com.touchpay.dto.RegisterDto
import com.touchpay.dto.zoop.SellerRegisterDto
import com.touchpay.exceptions.HasCredentialByEmailException
import com.touchpay.exceptions.HasCredentialByLoginException
import com.touchpay.persistence.dao.CredentialDao
import io.reactivex.Single
import org.mindrot.jbcrypt.BCrypt
import java.time.LocalDate
import java.time.LocalDateTime
import javax.inject.Inject

class RegisterService @Inject constructor(private val passwordService: PasswordService,
                                          private val dao: CredentialDao,
                                          private val zoopConsumer: ZoopConsumer) {

    fun register(dto: RegisterDto) = dao.hasByEmail(dto.email).doOnSuccess {
        if(it) {
            throw HasCredentialByEmailException()
        }
    }.flatMap {
        dao.hasByLogin(dto.login).doOnSuccess { hasLogin ->
            if(hasLogin) {
                throw HasCredentialByLoginException()
            }
        }
    }.flatMap {
        val pin = passwordService.generateCodePassword()
        zoopConsumer.createSeller(SellerRegisterDto(
                first_name = dto.name,
                email = dto.email,
                phone_number = dto.phone,
                taxpayer_id = dto.cpf,
                birthdate = dto.birthDate.toString(),
                created_at = LocalDateTime.now(),
                delinquent = false
        )).flatMap { id ->
            dao.register(Credential(
                    _id = null,
                    enabled = true,
                    block = false,
                    limit = 0.0,
                    cpf = dto.cpf,
                    login = dto.login,
                    name = dto.name,
                    address = dto.address,
                    transferMetadata = TransferMetadata(
                            date = LocalDate.now(),
                            value = 0.0
                    ),
                    phone = dto.phone,
                    email = dto.email,
                    password = BCrypt.hashpw(dto.password, BCrypt.gensalt()),
                    pin = BCrypt.hashpw(pin, BCrypt.gensalt()),
                    birthDate = dto.birthDate,
                    zoopId = id,
                    bankId = null))
        }.map { pin }
    }
}