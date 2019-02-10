package com.touchpay.exceptions

abstract class I18nException(val token: String) : Exception(token) {
    open fun formatArgs(): Array<Any>? {
        return null
    }
}
