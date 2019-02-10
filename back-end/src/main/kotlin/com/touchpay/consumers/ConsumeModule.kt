package com.touchpay.consumers

import com.touchpay.common.Auditor
import dagger.Module
import dagger.Provides
import io.vertx.core.json.JsonObject
import io.vertx.reactivex.core.Vertx
import io.vertx.reactivex.ext.web.client.WebClient
import javax.inject.Named
import javax.inject.Singleton

@Module
class ConsumeModule {
    @Provides
    @Singleton
    @Named("consume")
    fun provideAuditor() = Auditor("consume")

    @Provides
    @Singleton
    @Named("zoop")
    fun provideConsumeApiPlatform(vertx: Vertx, consumeObjectMapperManager: ConsumeObjectMapperManager, @Named("config") config: JsonObject, @Named("consume") auditor: Auditor): ConsumeApi {
        val key = "zoop"
        val consumeApiJsonObject = config.getJsonObject("consumeApi").getJsonObject(key)
        return ConsumeApi(WebClient.create(vertx),
                consumeObjectMapperManager[key],
                auditor,
                null,
                consumeApiJsonObject.getString("host"),
                consumeApiJsonObject.getBoolean("ssl",false),
                consumeApiJsonObject.getBoolean("abs",false),
                consumeApiJsonObject.getString("authorization"))
    }
}
