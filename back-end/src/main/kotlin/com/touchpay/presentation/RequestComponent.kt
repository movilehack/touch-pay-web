package com.touchpay.presentation

import com.touchpay.common.Translate
import dagger.Subcomponent

@RequestScope
@Subcomponent(modules = [(RequestModule::class)])
interface RequestComponent {
    val translate: Translate
}
