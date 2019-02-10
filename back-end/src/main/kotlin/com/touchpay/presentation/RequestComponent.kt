package com.touchpay.presentation

import com.touchpay.common.Translate
import com.touchpay.presentation.controllers.*
import dagger.Subcomponent

@RequestScope
@Subcomponent(modules = [(RequestModule::class)])
interface RequestComponent {
    val translate: Translate
    val authenticationController: AuthenticationController
    val controlController: ControlController
    val registerController: RegisterController
    val paymentController: PaymentController
    val bankController: BankController
}
