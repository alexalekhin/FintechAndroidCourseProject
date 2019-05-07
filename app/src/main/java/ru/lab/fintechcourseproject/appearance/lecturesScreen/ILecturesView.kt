package ru.lab.fintechcourseproject.appearance.lecturesScreen

import com.hannesdorfmann.mosby3.mvp.MvpView
import ru.lab.fintechcourseproject.database.lectures.DBLecture

interface ILecturesView : MvpView {
    fun onLoadingProgress()
    fun onResult(lectures: List<DBLecture>)
    fun onFailure()
}
