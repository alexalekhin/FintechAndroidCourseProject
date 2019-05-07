package ru.lab.fintechcourseproject.appearance.mainScreen.coursesTab.performance

import com.hannesdorfmann.mosby3.mvp.MvpView
import ru.lab.fintechcourseproject.database.students.DBStudent

interface IPerformanceView: MvpView {
    fun onResult(students: List<DBStudent>)
    fun onLoadingProgress()
    fun onFailure()
}