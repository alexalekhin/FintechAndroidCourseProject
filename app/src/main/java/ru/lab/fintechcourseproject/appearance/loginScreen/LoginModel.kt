package ru.lab.fintechcourseproject.appearance.loginScreen

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.lab.fintechcourseproject.Model
import ru.lab.fintechcourseproject.network.LoginInfo
import ru.lab.fintechcourseproject.network.NetworkService
import javax.inject.Inject

class LoginModel @Inject constructor(private val fintechService: NetworkService): Model {
    private val mutableLoginState = MutableLiveData<LoginState>()
    private var mutableCookie = MutableLiveData<String?>()
    val loginState: LiveData<LoginState> = mutableLoginState

    fun restoreSession(context: Context): Boolean {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        mutableCookie.value = prefs.getString(COOKIE_NAME, "")
        return mutableCookie.value!!.isNotEmpty()
    }

    fun login(email: String, password: String) {

        val emailRegex =
            Regex("(?:[a-z0-9!#\$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#\$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9]))\\.){3}(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9])|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)])")
        val passwordRegex = Regex(".{4,}")
        if (emailRegex.matches(email) && passwordRegex.matches(password)) {
            mutableLoginState.value = LoginState.IN_PROCESS
            fintechService.authorize(LoginInfo(email, password))
                .enqueue(object : Callback<Any> {
                    override fun onFailure(call: Call<Any>, t: Throwable) {
                        mutableLoginState.value = LoginState.FAILURE
                        mutableCookie.value = null
                    }

                    override fun onResponse(call: Call<Any>, response: Response<Any>) {
                        if (response.isSuccessful) {
                            mutableCookie.value = response.headers()
                                .get("Set-Cookie")
                                ?.split("; ")
                                ?.get(0).toString()
                            mutableLoginState.value = LoginState.LOGGED
                        } else {
                            mutableLoginState.value = LoginState.LOGGED
                        }


                    }
                })
        } else {
            mutableLoginState.value = LoginState.NOT_LOGGED
        }

    }

    fun saveCookieToSharedPreferences(context: Context) {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        with(prefs.edit()) {
            putString(COOKIE_NAME, mutableCookie.value)
            apply()
        }
    }

    companion object {
        const val PREFS_NAME = "ru.lab.fintechcourseproject"
        const val COOKIE_NAME = "cookie"
    }

    enum class LoginState {
        LOGGED, NOT_LOGGED, FAILURE, IN_PROCESS
    }
}