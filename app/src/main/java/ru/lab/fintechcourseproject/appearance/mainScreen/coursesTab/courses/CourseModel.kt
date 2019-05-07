package ru.lab.fintechcourseproject.appearance.mainScreen.coursesTab.courses

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.lab.fintechcourseproject.Model
import ru.lab.fintechcourseproject.appearance.lecturesScreen.LecturesModel
import ru.lab.fintechcourseproject.database.AppDb
import ru.lab.fintechcourseproject.database.courses.DBCourse
import ru.lab.fintechcourseproject.network.NetworkService
import ru.lab.fintechcourseproject.network.courses.CoursesContainer
import ru.lab.fintechcourseproject.network.courses.convertToDatabaseCourse
import javax.inject.Inject

class CourseModel @Inject constructor(
    private val fintechService: NetworkService,
    private val database: AppDb,
    private val context: Context
) : Model {

    private var mutableCourses: MutableLiveData<List<DBCourse>> = MutableLiveData<List<DBCourse>>().apply {
        value = database.getCoursesDao().getCourses()
    }
    private var mutableCoursesState: MutableLiveData<State> = MutableLiveData<State>().apply {
        value = State.LOADED_FROM_MEM
    }
    val courses: LiveData<List<DBCourse>> = mutableCourses
    val coursesState: LiveData<State> = mutableCoursesState

    fun loadCourses(source: CoursePresenter.DataSource) {
        mutableCoursesState.value = State.IN_PROCESS
        when (source) {
            CoursePresenter.DataSource.UPDATE -> updateCourses()
            CoursePresenter.DataSource.RELOAD -> mutableCourses.value = database.getCoursesDao().getCourses()
            CoursePresenter.DataSource.UNSPECIFIED -> {
                val coursesFromMemory = database.getCoursesDao().getCourses()
                if (coursesFromMemory.isEmpty()) {
                    updateCourses()
                } else {
                    mutableCourses.value = coursesFromMemory
                    mutableCoursesState.value = State.LOADED_FROM_MEM
                }
            }
        }
    }

    private fun updateCourses() {
        fintechService.getCourses(getCookieFromMemory())
            .enqueue(object : Callback<CoursesContainer> {
                override fun onFailure(call: Call<CoursesContainer>, t: Throwable) {
                    mutableCoursesState.value = State.NOT_LOADED
                }

                override fun onResponse(call: Call<CoursesContainer>, response: Response<CoursesContainer>) {
                    if (response.isSuccessful) {
                        val coursesContainer = response.body()!!
                        val newCourses = arrayListOf<DBCourse>()
                        for (course in coursesContainer.courses) {
                            newCourses.add(convertToDatabaseCourse(course))
                        }
                        mutableCourses.value = newCourses
                        saveCourseUrl()
                        mutableCoursesState.value = State.LOADED_FROM_NETWORK
                    } else {
                        mutableCoursesState.value = State.NOT_LOADED
                    }
                }
            })
    }

    private fun saveCourseUrl() {
        val prefs = context.getSharedPreferences(LecturesModel.PREFS_NAME, Context.MODE_PRIVATE)
        with(prefs.edit()) {
            putString(COURSE_URL, mutableCourses.value!![0].url)
            apply()
        }
    }

    private fun getCookieFromMemory(): String {
        val prefs = context.getSharedPreferences(LecturesModel.PREFS_NAME, Context.MODE_PRIVATE)
        return prefs.getString(LecturesModel.COOKIE_NAME, "")!!
    }

    companion object {
        const val COURSE_URL = "course_url"
    }

    enum class State {
        NOT_LOADED, IN_PROCESS, LOADED_FROM_MEM, LOADED_FROM_NETWORK
    }
}
