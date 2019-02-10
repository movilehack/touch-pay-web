package com.touchpay.business

import com.touchpay.domain.Credential
import com.touchpay.dto.RegisterDto
import com.touchpay.persistence.dao.CredentialDao
import io.reactivex.Single
import org.mindrot.jbcrypt.BCrypt
import javax.inject.Inject

class RegisterService @Inject constructor(private val passwordService: PasswordService, private val dao: CredentialDao) {
    fun register(dto: RegisterDto): Single<String> {
        val pin = BCrypt.hashpw(passwordService.generateCodePassword(), BCrypt.gensalt())
        return dao.register(Credential(
                _id = null,
                enabled = true,
                block = false,
                limit = 0.0,
                cpf = dto.cpf,
                login = dto.login,
                name = dto.name,
                address = dto.address,
                phone = dto.phone,
                email = dto.email,
                password = BCrypt.hashpw(dto.password, BCrypt.gensalt()),
                pin = pin,
                birthDate = dto.birthDate
        )).map {
            pin
        }
    }
}