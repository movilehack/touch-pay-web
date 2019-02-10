package com.touchpay.business

import com.touchpay.domain.Credential
import com.touchpay.dto.BlockDto
import com.touchpay.dto.ChangeLimitDto
import com.touchpay.exceptions.PasswordNotMatchException
import com.touchpay.persistence.dao.ControlDao
import io.reactivex.Completable
import io.reactivex.Single
import org.mindrot.jbcrypt.BCrypt
import javax.inject.Inject

class ControlService @Inject constructor(private val credential: Credential?, private val passwordService: PasswordService, private val dao: ControlDao) {
    fun changeLimit(dto: ChangeLimitDto) = dao.changeLimit(credential?._id!!, dto.newLimit)

    fun changeBlock(dto: BlockDto, block: Boolean): Completable {
        checkPassword(dto)
        return dao.changeBlock(credential?._id!!, block)
    }

    fun generatePin(dto: BlockDto): Single<String> {
        checkPassword(dto)
        val pin = passwordService.generateCodePassword()
        return dao.changePin(credential?._id!!, pin).toSingleDefault(pin)
    }

    private fun checkPassword(dto: BlockDto) {
        if (!BCrypt.checkpw(dto.password, credential?.password)) {
            throw PasswordNotMatchException()
        }
    }
}