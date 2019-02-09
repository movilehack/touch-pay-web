package com.touchpay.presentation

import com.touchpay.business.BusinessModule
import com.touchpay.consumers.ConsumeModule
import com.touchpay.persistence.PersistenceModule
import com.touchpay.presentation.routers.RouterBuilder
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [(PresentationModule::class),
    (ConsumeModule::class),
    (BusinessModule::class),
    (PersistenceModule::class)])
interface PresentationComponent {
    val routerBuilder: RouterBuilder
    fun requestComponent(requestModule: RequestModule): RequestComponent
}