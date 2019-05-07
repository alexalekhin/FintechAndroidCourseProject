package ru.lab.fintechcourseproject.appearance.mainScreen.profileTab


import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.hannesdorfmann.mosby3.mvp.MvpFragment
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.layout_profile_info.*
import ru.lab.fintechcourseproject.ModelType
import ru.lab.fintechcourseproject.R
import ru.lab.fintechcourseproject.appearance.mainScreen.OnFragmentInteractionListener
import ru.lab.fintechcourseproject.getModel
import ru.lab.fintechcourseproject.network.RetrofitNetworkClient
import ru.lab.fintechcourseproject.network.users.User

class UserProfileFragment : MvpFragment<IUserProfileView, UserProfilePresenter>(), IUserProfileView {
    private var listener: OnFragmentInteractionListener? = null



    override fun createPresenter(): UserProfilePresenter {
        return UserProfilePresenter(getModel(this.activity!!, ModelType.USER_MODEL) as UserProfileModel)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
        } else {
            throw IllegalStateException("$context must implement OnFragmentInteractionListener")
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.getUserProfile()
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    override fun onLoadingProgress() {
    }

    override fun onResult(user: User) {
        //main data
        tv_lastname.text = user.lastName
        tv_firstname.text = user.firstName
        tv_patronymic.text = user.patronymic
        tv_birth_date.text = user.bDay
        //education
        tv_school_value.text = user.school
        tv_university_value.text = user.university

        //contacts
        tv_phone_value.text = user.phone
        tv_email_value.text = user.email

        //other
        tv_work_value.text = user.currentWork
        Picasso.get()
            .load(
                RetrofitNetworkClient.BASE_URL.substring(
                    0,
                    RetrofitNetworkClient.BASE_URL.length - 1
                ) + user.avatarUrl
            )
//            .resize(iv_avatar.width, iv_avatar.height)
//            .centerCrop()
//            .fit()
            .into(iv_avatar)
    }

    override fun onFailure() {
        Toast.makeText(this.activity, "Нет доступа к сети", Toast.LENGTH_SHORT).show()
    }

    companion object {
        const val TAG = "UserProfileFragment"
        fun newInstance() = UserProfileFragment()
    }
}
