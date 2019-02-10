package com.touchpay.business

import com.touchpay.domain.Credential
import com.touchpay.domain.TransferMetadata
import com.touchpay.dto.BlockDto
import com.touchpay.dto.RegisterDto
import com.touchpay.persistence.dao.CredentialDao
import io.reactivex.Single
import org.mindrot.jbcrypt.BCrypt
import java.time.LocalDate
import javax.inject.Inject

class RegisterService @Inject constructor(private val passwordService: PasswordService, private val dao: CredentialDao) {
    fun register(dto: RegisterDto): Single<String> {
        val pin = passwordService.generateCodePassword()
        return dao.register(Credential(
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
                pin = pin,
                birthDate = dto.birthDate
        )).map {
            pin
        }
    }
}