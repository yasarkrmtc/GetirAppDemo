package com.getir.data.repository

import android.app.Application
import android.content.Context
import com.getir.data.api.ServiceInterface
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideContext(application: Application): Context {
        return application.applicationContext
    }

    @Singleton
    @Provides
    fun serviceRepository(serviceInterface: ServiceInterface): ServiceRepository {
        return ServiceRepositoryImpl(serviceInterface)
    }

}