package com.touchpay.business.validators

import com.touchpay.common.Translate
import java.util.*

interface IValidatorCheck<T> {
    fun validate(entity: T, translate: Translate, errors: HashSet<String>)
}
