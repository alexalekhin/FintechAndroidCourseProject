package ru.lab.fintechcourseproject.appearance.performanceScreen

import com.hannesdorfmann.mosby3.mvp.MvpView
import ru.lab.fintechcourseproject.database.students.DBStudent

interface IStudentsView: MvpView {

    fun updateStudentsList(students: List<DBStudent>)

    fun onLoadingProgress()

    fun onResult(students: List<DBStudent>)

    fun onFailure()
}
