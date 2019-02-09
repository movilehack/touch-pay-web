package com.touchpay.domain.exceptions

class ValidatorException(val errors: HashSet<String>) : Exception()
