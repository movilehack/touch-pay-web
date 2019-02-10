package com.touchpay.business.validators

import com.touchpay.common.Translate

class ValidatorContext<out T : Any?> constructor(val name: String, val value: T, private val translate: Translate, private val errors: HashSet<String>) {
    fun error(key: String) {
        errors.add(translate.get(key, translate.get(name), value))
    }
}
