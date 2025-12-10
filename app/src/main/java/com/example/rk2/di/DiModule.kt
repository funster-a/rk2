package com.example.rk2.di

import android.content.Context
import androidx.room.Room
import com.example.rk2.data.local.dao.TodoDao
import com.example.rk2.data.local.database.TodoDatabase
import com.example.rk2.data.repository.TodoRepositoryImpl
import com.example.rk2.domain.repository.TodoRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Hilt Dependency Injection module.
 * Provides database, DAO, and repository instances.
 */
@Module
@InstallIn(SingletonComponent::class)
object DiModule {
    
    /**
     * Provides the Room Database instance.
     */
    @Provides
    @Singleton
    fun provideTodoDatabase(
        @ApplicationContext context: Context
    ): TodoDatabase {
        return Room.databaseBuilder(
            context,
            TodoDatabase::class.java,
            "todo_database"
        ).build()
    }
    
    /**
     * Provides the TodoDao instance.
     */
    @Provides
    @Singleton
    fun provideTodoDao(database: TodoDatabase): TodoDao {
        return database.todoDao()
    }
    
    /**
     * Provides the TodoRepository implementation.
     * Binds the implementation to the interface for dependency injection.
     */
    @Provides
    @Singleton
    fun provideTodoRepository(
        todoDao: TodoDao
    ): TodoRepository {
        return TodoRepositoryImpl(todoDao)
    }
}

