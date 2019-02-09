package com.touchpay.presentation

import com.touchpay.common.Auditor
import dagger.Module
import dagger.Provides
import io.vertx.core.http.HttpServerOptions
import io.vertx.core.json.JsonObject
import io.vertx.core.net.JksOptions
import io.vertx.kotlin.ext.auth.KeyStoreOptions
import io.vertx.kotlin.ext.auth.jwt.JWTAuthOptions
import io.vertx.reactivex.core.Vertx
import io.vertx.reactivex.core.http.HttpServer
import io.vertx.reactivex.ext.auth.jwt.JWTAuth
import javax.inject.Named
import javax.inject.Singleton

@Module
class PresentationModule constructor(private val vertx: Vertx, private val config: () -> JsonObject) {
    @Provides
    @Singleton
    fun provideVertx(): Vertx = vertx

    @Provides
    @Singleton
    @Named("presentation")
    fun provideAuditor() = Auditor("presentation")

    @Provides
    @Named("config")
    fun provideConfig(): JsonObject = config()

    @Provides
    @Singleton
    fun provideHttpServer(@Named("config") config: JsonObject): HttpServer{
        val certificate = config.getJsonObject("ssl")

        return if (certificate != null) vertx.createHttpServer(HttpServerOptions()
            .setLogActivity(true)
            .setSsl(true)
            .setKeyStoreOptions(
                JksOptions()
                    .setPath(certificate.getString("keystore"))
                    .setPassword(certificate.getString("password"))
            )) else vertx.createHttpServer()
    }

    @Provides
    @Singleton
    fun provideJWTAuth(@Named("config") config: JsonObject): JWTAuth {
        val auth = config.getJsonObject("auth")
        return JWTAuth.create(vertx, JWTAuthOptions(
            permissionsClaimKey = "permissions",
            keyStore = KeyStoreOptions( path = auth.getString("path"),
            password = auth.getString("password"))))
    }
}
