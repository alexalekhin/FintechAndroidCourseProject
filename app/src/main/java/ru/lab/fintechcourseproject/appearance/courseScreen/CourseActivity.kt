package ru.lab.fintechcourseproject.appearance.courseScreen

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_course.*
import retrofit2.Call
import retrofit2.Response
import ru.lab.fintechcourseproject.R
import ru.lab.fintechcourseproject.di.ContextModule
import ru.lab.fintechcourseproject.di.DaggerApplicationComponent
import ru.lab.fintechcourseproject.di.DataBaseModule
import ru.lab.fintechcourseproject.di.NetworkModule
import ru.lab.fintechcourseproject.network.NetworkService
import ru.lab.fintechcourseproject.network.RetrofitNetworkClient
import ru.lab.fintechcourseproject.network.courses.CourseAbout
import javax.inject.Inject

class CourseActivity : AppCompatActivity() {

    @Inject
    lateinit var fintechService: NetworkService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_course)

        DaggerApplicationComponent
            .builder()
            .networkModule(NetworkModule(RetrofitNetworkClient))
            .contextModule(ContextModule(this))
            .dataBaseModule(DataBaseModule())
            .build()
            .inject(this)
        var cookie: String
        var courseUrl: String
        with(this.getSharedPreferences("ru.lab.fintechcourseproject", Context.MODE_PRIVATE)) {
            cookie = getString("cookie", "")!!
            courseUrl = getString("course_url", "")!!
        }
        fintechService.getCourseAbout(cookie, courseUrl)
            .enqueue(object : retrofit2.Callback<CourseAbout> {
                override fun onFailure(call: Call<CourseAbout>, t: Throwable) {
                    finish()
                }

                override fun onResponse(call: Call<CourseAbout>, response: Response<CourseAbout>) {
                    if (response.isSuccessful) {
                        wv_course.loadDataWithBaseURL("", response.body()!!.html, "text/html", "UTF-8", "")
                    } else {
                        finish()
                    }
                }
            })
    }
}
