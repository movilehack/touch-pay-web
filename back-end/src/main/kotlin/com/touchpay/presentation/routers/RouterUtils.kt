package com.touchpay.presentation.routers

import com.touchpay.business.CredentialService
import com.touchpay.common.Auditor
import com.touchpay.serializers.SerializerBuilder
import io.vertx.core.json.JsonObject
import io.vertx.reactivex.core.Vertx
import io.vertx.reactivex.core.http.HttpServer
import javax.inject.Inject
import javax.inject.Named

class RouterUtils @Inject constructor(internal val vertx: Vertx,
      internal val server: HttpServer,
      @Named("presentation") internal val auditor: Auditor,
      @Named("config") internal val config: JsonObject,
      internal val credentialService: CredentialService,
      internal val serializerBuilder: SerializerBuilder)
