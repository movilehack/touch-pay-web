package com.touchpay.presentation

import com.touchpay.common.Translate
import com.touchpay.presentation.controllers.AuthenticationController
import dagger.Subcomponent

@RequestScope
@Subcomponent(modules = [(RequestModule::class)])
interface RequestComponent {
    val translate: Translate
    val authenticationController: AuthenticationController
}
