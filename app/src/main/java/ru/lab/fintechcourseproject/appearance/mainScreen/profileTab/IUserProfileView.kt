package ru.lab.fintechcourseproject.appearance.mainScreen.profileTab

import com.hannesdorfmann.mosby3.mvp.MvpView
import ru.lab.fintechcourseproject.network.users.User

interface IUserProfileView : MvpView {
    fun onLoadingProgress()
    fun onResult(user: User)
    fun onFailure()
}
