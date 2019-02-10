package com.touchpay.business

import com.touchpay.business.validators.ValidatorBuilder
import com.touchpay.business.validators.notEmpty
import com.touchpay.dto.SignInDto
import com.touchpay.exceptions.SignInException
import com.touchpay.persistence.dao.AuthenticationDao
import io.reactivex.Single
import org.mindrot.jbcrypt.BCrypt
import javax.inject.Inject

class AuthenticationService @Inject constructor(validatorBuilder: ValidatorBuilder, private val dao: AuthenticationDao) {

    private val validatorSignInDto = validatorBuilder.create<SignInDto> {
        let { email } check {
            withName("email")
            notEmpty()
        }
        let { password } check {
            withName("password")
            notEmpty()
        }
    }

    fun signIn(dto: SignInDto): Single<String> {
        validatorSignInDto.validate(dto)
        return dao.getCredentialByEmail(dto.email).map {
            if (!it.isPresent || !BCrypt.checkpw(dto.password, it.get())) {
                throw SignInException()
            }

            it.get()
        }
    }
}