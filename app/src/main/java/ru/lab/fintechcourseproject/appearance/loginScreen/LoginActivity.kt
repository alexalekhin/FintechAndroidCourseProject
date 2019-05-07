package ru.lab.fintechcourseproject.appearance.loginScreen

import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.hannesdorfmann.mosby3.mvp.MvpActivity
import kotlinx.android.synthetic.main.activity_login.*
import ru.lab.fintechcourseproject.ModelType
import ru.lab.fintechcourseproject.R
import ru.lab.fintechcourseproject.getModel

class LoginActivity : MvpActivity<ILoginView, LoginPresenter>(), ILoginView {
    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        pb_login.visibility = View.INVISIBLE
        btn_login.setOnClickListener {
            presenter.login(et_email.text.toString(), et_password.text.toString())
        }
        presenter.restoreSession()
    }

    override fun createPresenter() = LoginPresenter(this, getModel(this.application, ModelType.LOGIN_MODEL)  as LoginModel)


    override fun onResult() {
        pb_login.visibility = View.INVISIBLE
        presenter.startMainActivity()
        finish()
    }

    override fun onFailure() {
        pb_login.visibility = View.INVISIBLE
        btn_login.isEnabled = true
        et_email.isEnabled = true
        et_password.isEnabled = true
        Toast.makeText(this, "Ошибка входа. Проверьте данные.", Toast.LENGTH_LONG).show()
    }

    override fun onLoadingProgress() {
        pb_login.visibility = View.VISIBLE
        btn_login.isEnabled = false
        et_email.isEnabled = false
        et_password.isEnabled = false
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
    }
}
