package ru.lab.fintechcourseproject.appearance.mainScreen.coursesTab.rating

import com.hannesdorfmann.mosby3.mvp.MvpView

interface IRatingView: MvpView {
    fun onResult(rating: RatingData)
    fun onLoadingProgress()
    fun onFailure()
}
