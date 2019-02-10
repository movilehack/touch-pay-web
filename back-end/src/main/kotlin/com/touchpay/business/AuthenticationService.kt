package com.touchpay.business

import com.touchpay.business.validators.ValidatorBuilder
import com.touchpay.business.validators.notEmpty
import com.touchpay.common.Auditor
import com.touchpay.dto.SignInDto
import com.touchpay.exceptions.SignInException
import com.touchpay.persistence.dao.AuthenticationDao
import com.touchpay.persistence.json.JsonBuilder
import io.reactivex.Single
import io.vertx.reactivex.ext.auth.jwt.JWTAuth
import org.mindrot.jbcrypt.BCrypt
import java.time.LocalDateTime
import java.time.ZoneId
import javax.inject.Inject
import javax.inject.Named

class AuthenticationService @Inject constructor(validatorBuilder: ValidatorBuilder,
                                                private val auth: JWTAuth,
                                                @Named("business") private val auditor: Auditor,
                                                private val dao: AuthenticationDao) {

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

            val now = LocalDateTime.now()
            val json = JsonBuilder {
                //sub (subject) = Entidade à quem o token pertence, normalmente o ID do usuário;
                "sub" to it.get()
                //iss (issuer) = Emissor do token;
                "iss" to "touchpay"
                //iat (issued at) = Timestamp de quando o token foi criado;
                "iat" to now.atZone(ZoneId.systemDefault()).toEpochSecond()
                //exp (expiration) = Timestamp de quando o token irá expirar;
                "exp" to now.plusDays(3).atZone(ZoneId.systemDefault()).toEpochSecond()
                //aud (audience) = Destinatário do token, representa a aplicação que irá usá-lo.
                "aud" to "front-end"
                "permissions" to arrayOf<String>()
            }.json

            auditor.info("jwt-payload -> {0}", json)

            auth.generateToken(json)
        }
    }
}