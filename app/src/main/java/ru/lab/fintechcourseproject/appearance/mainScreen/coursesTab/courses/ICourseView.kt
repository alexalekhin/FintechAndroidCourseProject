package ru.lab.fintechcourseproject.appearance.mainScreen.coursesTab.courses

import com.hannesdorfmann.mosby3.mvp.MvpView
import ru.lab.fintechcourseproject.database.courses.DBCourse

interface ICourseView : MvpView {
    fun onResult(courses: List<DBCourse>)
    fun onLoadingProgress()
    fun onFailure()
}
