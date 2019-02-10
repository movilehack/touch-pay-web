package com.touchpay.exceptions

class ValidatorException(val errors: HashSet<String>) : Exception()
