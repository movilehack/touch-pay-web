package com.touchpay.business

import com.touchpay.domain.Credential
import com.touchpay.dto.BlockDto
import com.touchpay.dto.ChangeLimitDto
import com.touchpay.exceptions.PasswordNotMatchException
import com.touchpay.persistence.dao.ControlDao
import io.reactivex.Completable
import org.mindrot.jbcrypt.BCrypt
import javax.inject.Inject

class ControlService @Inject constructor(private val credential: Credential?, private val dao: ControlDao) {
    fun changeLimit(dto: ChangeLimitDto) = dao.changeLimit(credential?._id!!, dto.newLimit)
    fun changeBlock(dto: BlockDto, block: Boolean): Completable {
        if (!BCrypt.checkpw(dto.password, credential?.password)) {
            throw PasswordNotMatchException()
        }
        return dao.changeBlock(credential?._id!!, block)
    }
}