package com.velichkomarija.myapplication

import com.velichkomarija.myapplication.data.functions.FunctionsDataRepository
import com.velichkomarija.myapplication.data.functions.LocalFunctionsDataRepository
import com.velichkomarija.myapplication.data.user.LocalUserRepository
import com.velichkomarija.myapplication.data.user.UserDataRepository
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