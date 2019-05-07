package ru.lab.fintechcourseproject.appearance.loginScreen

import com.hannesdorfmann.mosby3.mvp.MvpView

interface ILoginView: MvpView {
    fun onLoadingProgress()
    fun onResult()
    fun onFailure()
}
