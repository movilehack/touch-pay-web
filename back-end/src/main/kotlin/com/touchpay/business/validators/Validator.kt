package com.touchpay.business.validators

import com.touchpay.common.Translate
import com.touchpay.exceptions.ValidatorException
import java.util.*

@ValidatorMarker
class Validator<T> constructor(private val translate: Translate) {
    private val checks = HashSet<IValidatorCheck<T>>()

    fun validate(entity: T) {
        val errors = HashSet<String>()

        for (item in checks) {
            item.validate(entity, translate, errors)
        }

        if (errors.any()) {
            throw ValidatorException(errors)
        }
    }

    private fun <TValidatorCheck : IValidatorCheck<T>> add(validatorCheck: TValidatorCheck): TValidatorCheck {
        checks.add(validatorCheck)
        return validatorCheck
    }

    fun <TResult> let(action: T.() -> TResult) = add(ValidatorCheck(ValidatorType(action)))
}
