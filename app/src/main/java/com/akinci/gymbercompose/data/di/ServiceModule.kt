package com.akinci.gymbercompose.data.di

import com.akinci.gymbercompose.common.repository.BaseRepository
import com.akinci.gymbercompose.data.api.PartnerServiceDao
import com.akinci.gymbercompose.data.repository.PartnerRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ServiceModule {

    @Provides
    @Singleton
    fun providePartnerService(
        retrofit: Retrofit
    ): PartnerServiceDao = retrofit.create(PartnerServiceDao::class.java)

    @Provides
    @Singleton
    fun providePartnerRepository(
        partnerServiceDao: PartnerServiceDao,
        baseRepository: BaseRepository,
    ) = PartnerRepository(partnerServiceDao, baseRepository)

}