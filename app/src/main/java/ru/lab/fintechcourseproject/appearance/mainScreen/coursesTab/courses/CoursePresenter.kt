package ru.lab.fintechcourseproject.appearance.mainScreen.coursesTab.courses

import android.content.Context
import android.content.Intent
import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter
import ru.lab.fintechcourseproject.appearance.courseScreen.CourseActivity

class CoursePresenter(private val context: Context, private val model: CourseModel) : MvpBasePresenter<ICourseView>() {
    fun loadCourse(source: DataSource = DataSource.UNSPECIFIED) {
        model.loadCourses(source)
        model.coursesState.observeForever {
            ifViewAttached { view ->
                when (it) {
                    CourseModel.State.LOADED_FROM_NETWORK -> view.onResult(model.courses.value!!)
                    CourseModel.State.LOADED_FROM_MEM -> view.onResult(model.courses.value!!)
                    CourseModel.State.IN_PROCESS -> view.onLoadingProgress()
                    CourseModel.State.NOT_LOADED -> view.onFailure()
                    else -> TODO()
                }
            }
        }
    }


    fun showCourseAbout() {
        val intent = Intent(context, CourseActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
        }
        context.startActivity(intent)
    }


    enum class DataSource {
        UPDATE, RELOAD, UNSPECIFIED
    }
}