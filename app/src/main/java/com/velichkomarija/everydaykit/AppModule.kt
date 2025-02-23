package com.velichkomarija.everydaykit

import android.content.Context
import com.velichkomarija.everydaykit.data.functions.FunctionsDataRepository
import com.velichkomarija.everydaykit.data.functions.LocalFunctionsDataRepository
import com.velichkomarija.everydaykit.data.user.LocalUserRepository
import com.velichkomarija.everydaykit.data.user.UserDataRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideAppContext(@ApplicationContext context: Context): Context = context

    @Provides
    @Singleton
    fun provideDataUserRepository(): UserDataRepository = LocalUserRepository()

    @Provides
    @Singleton
    fun provideFunctionsRepository(
        @ApplicationContext context: Context
    ): FunctionsDataRepository = LocalFunctionsDataRepository(context)
}