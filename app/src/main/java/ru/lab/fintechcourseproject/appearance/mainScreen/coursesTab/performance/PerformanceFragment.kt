package ru.lab.fintechcourseproject.appearance.mainScreen.coursesTab.performance

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.hannesdorfmann.mosby3.mvp.MvpFragment
import kotlinx.android.synthetic.main.fragment_performance.*
import ru.lab.fintechcourseproject.ModelType
import ru.lab.fintechcourseproject.R
import ru.lab.fintechcourseproject.appearance.performanceScreen.PerformanceDetailsActivity
import ru.lab.fintechcourseproject.database.students.DBStudent
import ru.lab.fintechcourseproject.getModel

class PerformanceFragment : MvpFragment<IPerformanceView, PerformancePresenter>(), IPerformanceView {

    private var performancePeopleAdapter: PerformancePeopleAdapter? = null

    override fun createPresenter(): PerformancePresenter {
        return PerformancePresenter(getModel(this.activity!!, ModelType.COURSE_PROGRESS_MODEL) as PerformanceModel)
    }

    override fun onResult(students: List<DBStudent>) {
        pb_performance.visibility = View.INVISIBLE
        rv_performance_persons.visibility = View.VISIBLE
        updateStudentsData(students)
    }

    override fun onLoadingProgress() {
        pb_performance.visibility = View.VISIBLE
        rv_performance_persons.visibility = View.INVISIBLE
    }

    override fun onFailure() {
        pb_performance.visibility = View.INVISIBLE
        rv_performance_persons.visibility = View.VISIBLE
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_performance, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rv_performance_persons.layoutManager = LinearLayoutManager(this.activity, LinearLayoutManager.HORIZONTAL, false)
        setupPersonsAdapter(arrayListOf())
        layout_academic_progress_header.setOnClickListener {
            val intent = Intent(activity, PerformanceDetailsActivity::class.java)
            startActivity(intent)
        }
        presenter.loadStudents()
    }

    private fun updateStudentsData(students: List<DBStudent>) {
        val performancePeople: ArrayList<PerformancePerson> = ArrayList<PerformancePerson>().apply {
            students.forEach { student -> add(PerformancePerson(student.name, student.points.toInt())) }
        }
        performancePeopleAdapter?.people = performancePeople
        performancePeopleAdapter?.notifyDataSetChanged()
    }

    private fun setupPersonsAdapter(people: ArrayList<PerformancePerson>) {
        performancePeopleAdapter = PerformancePeopleAdapter(people)
        rv_performance_persons.adapter = performancePeopleAdapter
    }

    companion object {
        fun newInstance() =
            PerformanceFragment().apply {}

        const val TAG = "PerformanceFragment"
    }
}
