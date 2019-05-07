package ru.lab.fintechcourseproject.appearance.mainScreen.profileTab

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.lab.fintechcourseproject.Model
import ru.lab.fintechcourseproject.database.user.UserRepository
import ru.lab.fintechcourseproject.network.NetworkService
import ru.lab.fintechcourseproject.network.users.Profile
import ru.lab.fintechcourseproject.network.users.User
import javax.inject.Inject

class UserProfileModel @Inject constructor(
    private val fintechService: NetworkService,
    private val context: Context
) : Model {
    private val userRepository = UserRepository(
        context.getSharedPreferences(
            PREFS_NAME,
            Context.MODE_PRIVATE
        )
    )
    private var mutableUser = MutableLiveData<User>().apply {
        value = userRepository.loadUserFromMemory()
    }
    val user: User? = mutableUser.value

    private val mutableProfileState = MutableLiveData<ProfileState>()
    val profileState: LiveData<ProfileState> = mutableProfileState

    fun loadUserProfile() {
        val cookie: String = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE).getString(COOKIE_NAME, "")!!
        fintechService.getProfile(cookie)
            .enqueue(object : Callback<Profile> {
                override fun onFailure(call: Call<Profile>, t: Throwable) {
                    mutableProfileState.value = ProfileState.NOT_LOADED
                }

                override fun onResponse(call: Call<Profile>, response: Response<Profile>) {
                    if (response.isSuccessful) {
                        userRepository.saveUserToMemory(response.body()!!.user)
                        mutableUser.value = response.body()!!.user
                        mutableProfileState.value = ProfileState.LOADED_FROM_NETWORK
                    }
                }
            })
    }

    companion object {
        const val PREFS_NAME = "ru.lab.fintechcourseproject"
        const val COOKIE_NAME = "cookie"
    }

    enum class ProfileState {
        NOT_LOADED, IN_PROCESS, LOADED_FROM_MEM, LOADED_FROM_NETWORK
    }
}
