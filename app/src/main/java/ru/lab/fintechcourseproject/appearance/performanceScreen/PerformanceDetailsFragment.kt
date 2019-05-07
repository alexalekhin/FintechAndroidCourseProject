package ru.lab.fintechcourseproject.appearance.performanceScreen

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.hannesdorfmann.mosby3.mvp.MvpFragment
import ru.lab.fintechcourseproject.ModelType
import ru.lab.fintechcourseproject.R
import ru.lab.fintechcourseproject.database.students.DBStudent
import ru.lab.fintechcourseproject.getModel

class PerformanceDetailsFragment : MvpFragment<IStudentsView, StudentsPresenter>(), IStudentsView,
    SwipeRefreshLayout.OnRefreshListener {

    private var listener: OnProgressFragmentInteractionListener? = null
    private lateinit var progressBar: ProgressBar
    private lateinit var recyclerView: RecyclerView
    lateinit var studentsAdapter: StudentsAdapter
    private lateinit var swipeRefresh: SwipeRefreshLayout

    override fun createPresenter(): StudentsPresenter =
        StudentsPresenter(getModel(this.activity!!.application, ModelType.STUDENTS_MODEL) as StudentsModel)

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnProgressFragmentInteractionListener) {
            listener = context
        } else {
            throw IllegalStateException("$context must implement OnProgressFragmentInteractionListener")
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_performance_details, container, false)
        swipeRefresh = view.findViewById(R.id.swipe_refresh_students)
        swipeRefresh.setOnRefreshListener(this)
        progressBar = view.findViewById(R.id.pb_students)
        recyclerView = view.findViewById(R.id.rv_contacts_container)
        recyclerView.layoutManager = LinearLayoutManager(this.activity)
//        val decoration =
//            CustomSeparatorItemDecoration(this.activity!!, CustomSeparatorItemDecoration.DividerType.VERTICAL)
//        recyclerView.itemAnimator = CustomRecyclerViewAnimator()
//        recyclerView.addItemDecoration(decoration)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAdapter()
    }

    private fun setupAdapter() {
        studentsAdapter = StudentsAdapter(arrayListOf())
        recyclerView.adapter = studentsAdapter
        presenter.getStudents()
    }

    override fun updateStudentsList(students: List<DBStudent>) {
        studentsAdapter.students = ArrayList(students)
        studentsAdapter.notifyDataSetChanged()
    }

    override fun onRefresh() = presenter.getStudents(StudentsPresenter.DataSource.UPDATE)

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    override fun onLoadingProgress() {
        progressBar.visibility = View.VISIBLE
    }

    override fun onResult(students: List<DBStudent>) {
        studentsAdapter.students = ArrayList(students)
        studentsAdapter.notifyDataSetChanged()
        swipeRefresh.isRefreshing = false
        progressBar.visibility = View.INVISIBLE
        listener?.resetSorting()
    }

    override fun onFailure() {
        swipeRefresh.isRefreshing = false
        progressBar.visibility = View.INVISIBLE
        listener?.resetSorting()
        Toast.makeText(this.activity, "Нет доступа к сети", Toast.LENGTH_SHORT).show()
    }

    companion object {
        fun newInstance(): PerformanceDetailsFragment = PerformanceDetailsFragment()

        const val TAG = "PerformanceDetailsFragment"
    }
}
