package ru.lab.fintechcourseproject.appearance.mainScreen.coursesTab.courses

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.hannesdorfmann.mosby3.mvp.MvpFragment
import kotlinx.android.synthetic.main.fragment_current_course.*
import org.threeten.bp.Instant
import org.threeten.bp.LocalDateTime
import org.threeten.bp.ZoneId
import ru.lab.fintechcourseproject.ModelType
import ru.lab.fintechcourseproject.R
import ru.lab.fintechcourseproject.database.courses.DBCourse
import ru.lab.fintechcourseproject.getModel


class CurrentCourseFragment : MvpFragment<ICourseView, CoursePresenter>(), ICourseView {

    override fun createPresenter(): CoursePresenter {
        return CoursePresenter(this.activity!!, getModel(this.activity!!, ModelType.COURSES_MODEL) as CourseModel)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_current_course, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        curr_course_header.setOnClickListener {
            presenter.showCourseAbout()
        }
        presenter.loadCourse()
    }

    override fun onResult(courses: List<DBCourse>) {
        val course = courses[0]
        with(tv_course_title) {
            text = course.title
            visibility = View.VISIBLE
        }
        with(tv_course_status) {
            text = course.status
            visibility = View.VISIBLE
        }
        with(tv_course_date) {
            val instant: Instant = Instant.parse(course.date)
            val localDateTime: LocalDateTime = instant.atZone(ZoneId.systemDefault()).toLocalDateTime()
            text = "${localDateTime.dayOfMonth} ${localDateTime.month} ${localDateTime.year}"
            visibility = View.VISIBLE
        }

        pb_course_info.visibility = View.INVISIBLE
    }

    override fun onLoadingProgress() {
        pb_course_info.visibility = View.VISIBLE
        tv_course_title.visibility = View.INVISIBLE
        tv_course_status.visibility = View.INVISIBLE
        tv_course_date.visibility = View.INVISIBLE
    }

    override fun onFailure() {
        pb_course_info.visibility = View.INVISIBLE
        tv_course_title.visibility = View.VISIBLE
        tv_course_status.visibility = View.VISIBLE
        tv_course_date.visibility = View.VISIBLE
        Toast.makeText(this.activity, "Ошибка подключения. Проверьте сеть.", Toast.LENGTH_LONG).show()
    }

    companion object {
        fun newInstance() =
            CurrentCourseFragment()

        const val TAG = "CurrentCourseFragment"
    }
}
