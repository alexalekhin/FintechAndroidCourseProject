package ru.lab.fintechcourseproject.di

import android.content.SharedPreferences
import dagger.Component
import ru.lab.fintechcourseproject.database.AppDb
import ru.lab.fintechcourseproject.network.NetworkService
import javax.inject.Singleton

@Component(modules = [NetworkModule::class, DataBaseModule::class, ContextModule::class])
@Singleton
interface ApplicationComponent {
    fun getNetworkService(): NetworkService
    fun getDatabase(): AppDb
    fun getSharedPreferences(): SharedPreferences
}