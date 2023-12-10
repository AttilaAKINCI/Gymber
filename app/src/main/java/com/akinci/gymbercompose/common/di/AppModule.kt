package com.akinci.gymbercompose.common.di

import android.content.Context
import com.akinci.gymbercompose.BuildConfig
import com.akinci.gymbercompose.common.coroutine.CoroutineContextProvider
import com.akinci.gymbercompose.common.network.NetworkChecker
import com.akinci.gymbercompose.common.network.RestConfig
import com.akinci.gymbercompose.common.repository.BaseRepository
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class) // live as long as hole application
object AppModule {

    /** Coroutine context provider
     * START
     * **/
    @Provides
    @Singleton
    fun provideCoroutineContext() = CoroutineContextProvider()
    /** END **/

    /** Network Connection Checker Integration
     * START
     * **/
    @Provides
    @Singleton
    fun provideNetworkChecker(@ApplicationContext context: Context) : NetworkChecker = NetworkChecker(context)
    /** END **/

    /** Base Repository
     * START
     * **/
    @Provides
    fun provideBaseRepository(
        networkChecker: NetworkChecker
    ) : BaseRepository = BaseRepository(networkChecker)
    /** END **/

    /** Retrofit HILT Integrations
     * START
     * **/
    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class BaseURL

    @Provides
    @BaseURL
    fun provideBaseUrl(): String = RestConfig.API_BASE_URL

    @Provides
    fun provideMoshiConverterFactory(
        mosh: Moshi
    ): MoshiConverterFactory = MoshiConverterFactory.create(mosh)

    @Singleton
    @Provides
    fun providesMoshi(): Moshi = Moshi.Builder().build()

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class RestHttpClient

    @Provides
    @Singleton
    @RestHttpClient
    fun provideRestOkHttpClient() : OkHttpClient {
        val builder = OkHttpClient.Builder()

        if (BuildConfig.DEBUG) {
            // debug logging activated
            val logger = HttpLoggingInterceptor()
            logger.level = HttpLoggingInterceptor.Level.BODY

            //add logging interceptor
            builder.addInterceptor(logger)
        }

        return builder
            .addInterceptor(Interceptor { chain ->
                chain.proceed(
                    chain.request().newBuilder()
                        .addHeader(BuildConfig.AGENT, BuildConfig.AGENT_VAL)
                        .build()
                )
            })
            .readTimeout(100, TimeUnit.SECONDS)
            .connectTimeout(100, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(
        @RestHttpClient okHttpClient: OkHttpClient,
        @BaseURL baseURL: String,
        converter: MoshiConverterFactory
    ) : Retrofit = Retrofit.Builder()
        .baseUrl(baseURL)
        .client(okHttpClient)
        .addConverterFactory(converter)
        .build()
    /** END **/

}