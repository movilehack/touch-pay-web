package com.touchpay.presentation

import io.vertx.reactivex.core.AbstractVerticle

class MainVerticle : AbstractVerticle() {
    override fun start() {
        val presentation = DaggerPresentationComponent.builder()
                .presentationModule(PresentationModule(vertx, ::config))
                .build()

        presentation.routerBuilder.build {
            post("sign/in") go { authenticationController::signIn }
            onlyAuthenticated().to {
                post("change/limit") go { controlController::changeLimit }
                post("block") go { authenticationController::signIn }
                post("unblock") go { authenticationController::signIn }
                post("pay") go { authenticationController::signIn }
            }
        }
    }
}
