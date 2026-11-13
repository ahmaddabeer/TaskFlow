package com.example.taskflow.data.di

import android.content.Context
import androidx.room.Room
import com.example.taskflow.data.room.local.TaskDAO
import com.example.taskflow.data.room.local.TaskDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class TaskDatabaseModule {


    @Provides
    @Singleton
    fun taskDatabase(

        @ApplicationContext context: Context
    ): TaskDatabase {

        return Room.databaseBuilder(
            context,
            TaskDatabase::class.java,
            "task").build()


    }


    @Provides
    @Singleton
    fun provideDao(database: TaskDatabase): TaskDAO{

        return database.taskDao()

        


    }
}