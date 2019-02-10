package com.touchpay.business

import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PasswordService @Inject constructor() {
    private val maximumNumberLength = 6
    private val numeric = "0123456789".toCharArray()
    private val random = Random()

    fun generateCodePassword(): String {
        val stringBuilder = StringBuilder()

        for (i in 0 until maximumNumberLength) {
            stringBuilder.append(numeric[random.nextInt(numeric.size)])
        }

        return stringBuilder.toString()
    }
}