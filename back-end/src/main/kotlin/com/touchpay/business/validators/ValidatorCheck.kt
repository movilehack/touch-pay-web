package com.touchpay.business.validators

import com.touchpay.common.Translate
import java.util.*

class ValidatorCheck<T, TType>
    constructor(private val validatorType: ValidatorType<T, TType>) : IValidatorCheck<T> {
    override fun validate(entity: T, translate: Translate, errors: HashSet<String>) {
        validatorType.validate(entity, translate, errors)
    }

    infix fun check(init: ValidatorType<T, TType>.() -> Unit) {
        validatorType.init()
    }
}
