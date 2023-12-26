package com.akinci.gymber.core.di

import com.akinci.gymber.core.application.AppConfig
import com.akinci.gymber.core.coroutine.ContextProvider
import com.akinci.gymber.core.coroutine.ContextProviderImpl
import com.akinci.gymber.core.network.HttpClientFactory
import com.akinci.gymber.core.network.HttpEngineFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideContextProvider(): ContextProvider = ContextProviderImpl()

    @Provides
    @Singleton
    fun provideApiClient(
        httpEngineFactory: HttpEngineFactory,
        appConfig: AppConfig,
    ): HttpClient = HttpClientFactory(
        httpEngineFactory,
        appConfig,
    ).create()
}
