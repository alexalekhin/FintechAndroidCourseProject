package ru.lab.fintechcourseproject.di

import dagger.Component
import ru.lab.fintechcourseproject.FintechApplication
import ru.lab.fintechcourseproject.appearance.courseScreen.CourseActivity
import javax.inject.Singleton

@Component(modules = [NetworkModule::class, DataBaseModule::class, ContextModule::class])
@Singleton
interface ApplicationComponent {
    fun inject (app: FintechApplication)
    fun inject (courseActivity: CourseActivity)
}
