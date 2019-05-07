package ru.lab.fintechcourseproject.appearance.mainScreen.profileTab

import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter
import javax.inject.Inject

class UserProfilePresenter @Inject constructor(private val userProfileModel: UserProfileModel) :
    MvpBasePresenter<IUserProfileView>() {
    fun getUserProfile() {
        userProfileModel.loadUserProfile()
        userProfileModel.profileState.observeForever {
            ifViewAttached { view ->
                when (it) {
                    UserProfileModel.ProfileState.LOADED_FROM_NETWORK -> view.onResult(userProfileModel.user!!)

                    UserProfileModel.ProfileState.LOADED_FROM_MEM -> view.onResult(userProfileModel.user!!)

                    UserProfileModel.ProfileState.IN_PROCESS -> view.onLoadingProgress()
                    UserProfileModel.ProfileState.NOT_LOADED -> view.onFailure()
                    else -> TODO()
                }
            }
        }
    }
}
