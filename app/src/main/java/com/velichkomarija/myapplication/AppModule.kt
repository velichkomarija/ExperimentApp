package com.velichkomarija.myapplication

import com.velichkomarija.myapplication.data.FunctionsDataRepository
import com.velichkomarija.myapplication.data.LocalFunctionsDataRepository
import com.velichkomarija.myapplication.data.LocalUserRepository
import com.velichkomarija.myapplication.data.UserDataRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideDataUserRepository(): UserDataRepository = LocalUserRepository()

    @Provides
    @Singleton
    fun provideFunctionsRepository(): FunctionsDataRepository = LocalFunctionsDataRepository()
}