package ru.lab.fintechcourseproject.appearance.mainScreen.coursesTab

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import kotlinx.android.synthetic.main.fragment_courses.*
import ru.lab.fintechcourseproject.R
import ru.lab.fintechcourseproject.appearance.mainScreen.coursesTab.courses.CoursePresenter
import ru.lab.fintechcourseproject.appearance.mainScreen.coursesTab.courses.CurrentCourseFragment
import ru.lab.fintechcourseproject.appearance.mainScreen.coursesTab.performance.PerformanceFragment
import ru.lab.fintechcourseproject.appearance.mainScreen.coursesTab.performance.PerformancePresenter
import ru.lab.fintechcourseproject.appearance.mainScreen.coursesTab.rating.RatingFragment
import ru.lab.fintechcourseproject.appearance.mainScreen.coursesTab.rating.RatingPresenter

class CoursesFragment : SwipeRefreshLayout.OnRefreshListener, Fragment() {
    override fun onRefresh() {
        val courseFragment = childFragmentManager.findFragmentByTag(CurrentCourseFragment.TAG)
        if (courseFragment is CurrentCourseFragment) courseFragment.presenter.loadCourse(CoursePresenter.DataSource.UPDATE)

        val performanceFragment = childFragmentManager.findFragmentByTag(PerformanceFragment.TAG)
        if (performanceFragment is PerformanceFragment) performanceFragment.presenter.loadStudents(PerformancePresenter.DataSource.UPDATE)

        val ratingFragment = childFragmentManager.findFragmentByTag(RatingFragment.TAG)
        if (ratingFragment is RatingFragment) ratingFragment.presenter.loadRating(RatingPresenter.DataSource.UPDATE)

        swipe_refresh_courses.isRefreshing = false
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_courses, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        swipe_refresh_courses.setOnRefreshListener(this)
        childFragmentManager
            .beginTransaction()
            .replace(R.id.container_current_course, CurrentCourseFragment.newInstance(), CurrentCourseFragment.TAG)
            .commit()
        childFragmentManager
            .beginTransaction()
            .replace(R.id.container_academic_progress, PerformanceFragment.newInstance(), PerformanceFragment.TAG)
            .commit()
        childFragmentManager
            .beginTransaction()
            .replace(R.id.container_rating, RatingFragment.newInstance(), RatingFragment.TAG)
            .commit()
    }
//
//    override fun onAttach(context: Context) {
//        super.onAttach(context)
//        if (context is OnFragmentInteractionListener) {
//            listener = context
//        } else {
//            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
//        }
//    }
//
//    override fun onDetach() {
//        super.onDetach()
//        listener = null
//    }

    companion object {
        const val TAG = "CoursesFragment"
        @JvmStatic
        fun newInstance() = CoursesFragment()
    }
}
