package ru.lab.fintechcourseproject.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import ru.lab.fintechcourseproject.database.AppDb
import javax.inject.Singleton

@Module(includes = [ContextModule::class])
class DataBaseModule {
    @Provides
    @Singleton
    fun provideDatabase(context: Context): AppDb {
        return Room.databaseBuilder(context, AppDb::class.java, DATABASE_NAME)
            .allowMainThreadQueries()
            .build()
    }

    companion object {
        const val DATABASE_NAME = "hw-db"
    }
}