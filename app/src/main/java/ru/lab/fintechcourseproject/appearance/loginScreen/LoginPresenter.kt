package ru.lab.fintechcourseproject.appearance.loginScreen

import android.content.Context
import android.content.Intent
import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter
import ru.lab.fintechcourseproject.appearance.mainScreen.MainActivity
import javax.inject.Inject

class LoginPresenter @Inject constructor(private val context: Context, private val model: LoginModel) :
    MvpBasePresenter<ILoginView>() {

    init {
        model.loginState.observeForever {
            ifViewAttached { view ->
                when (it) {
                    LoginModel.LoginState.LOGGED -> {
                        model.saveCookieToSharedPreferences(context)
                        view.onResult()
                    }
                    LoginModel.LoginState.NOT_LOGGED -> view.onFailure()
                    LoginModel.LoginState.FAILURE -> view.onFailure()
                    LoginModel.LoginState.IN_PROCESS -> view.onLoadingProgress()
                    else -> TODO()
                }
            }
        }
    }

    fun restoreSession() {
        if (model.restoreSession(context)) {
            ifViewAttached { view -> view.onResult()}
        }
    }

    fun login(email: String, password: String) {
        model.login(email, password)
    }

    fun startMainActivity() {
        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
        }
        context.startActivity(intent)
    }
}
