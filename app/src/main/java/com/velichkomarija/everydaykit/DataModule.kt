package com.velichkomarija.everydaykit

import android.content.Context
import androidx.room.Room
import com.velichkomarija.everydaykit.data.todo.db.TaskDao
import com.velichkomarija.everydaykit.data.todo.db.TaskDatabase
import com.velichkomarija.everydaykit.data.todo.network.FirestoreDataSource
import com.velichkomarija.everydaykit.data.todo.network.NetworkSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideDataBase(@ApplicationContext context: Context): TaskDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            TaskDatabase::class.java,
            "Tasks.db"
        ).build()
    }

    @Provides
    fun provideTaskDao(database: TaskDatabase): TaskDao = database.taskDao()

    @Provides
    fun provideAuthService(): AuthService = AuthService()

    @Provides
    fun provideNetworkSource(): NetworkSource = FirestoreDataSource(provideAuthService())

}