package com.touchpay.presentation

import io.vertx.reactivex.core.AbstractVerticle

class MainVerticle : AbstractVerticle() {
    override fun start() {
        val presentation = DaggerPresentationComponent.builder()
                .presentationModule(PresentationModule(vertx, ::config))
                .build()

        presentation.routerBuilder.build {

        }
    }
}
