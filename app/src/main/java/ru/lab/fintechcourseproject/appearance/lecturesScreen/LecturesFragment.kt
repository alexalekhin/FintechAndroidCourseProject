package ru.lab.fintechcourseproject.appearance.lecturesScreen

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
import ru.lab.fintechcourseproject.database.lectures.DBLecture
import ru.lab.fintechcourseproject.getModel

class LecturesFragment : MvpFragment<ILecturesView, LecturesPresenter>(), ILecturesView,
    SwipeRefreshLayout.OnRefreshListener {

    private var listener: OnLectureFragmentInteractionListener? = null
    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var swipeRefresh: SwipeRefreshLayout
    private lateinit var lecturesAdapter: LecturesAdapter

    override fun createPresenter(): LecturesPresenter {
        return LecturesPresenter(
            getModel(activity!!.application, ModelType.LECTURES_MODEL) as LecturesModel
        )
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnLectureFragmentInteractionListener) {
            listener = context
        } else {
            throw IllegalStateException("$context must implement OnLectureFragmentInteractionListener")
        }
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_rating_details, container, false)

        progressBar = view.findViewById(R.id.pb_lectures)
        recyclerView = view.findViewById(R.id.rv_lectures)
        swipeRefresh = view.findViewById(R.id.swipe_refresh_lectures)
        recyclerView.layoutManager = LinearLayoutManager(this.activity)
        swipeRefresh.setOnRefreshListener(this)
        return view

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupLecturesAdapter()
    }

    override fun onRefresh() = presenter.loadLectures(LecturesPresenter.DataSource.NETWORK)

    private fun setupLecturesAdapter() {
        lecturesAdapter = LecturesAdapter(arrayListOf(), listener)
        recyclerView.adapter = lecturesAdapter
        presenter.loadLectures()
    }

    override fun onLoadingProgress() {
        progressBar.visibility = View.VISIBLE
    }

    override fun onResult(lectures: List<DBLecture>) {
        lecturesAdapter.lectures = lectures
        lecturesAdapter.notifyDataSetChanged()
        progressBar.visibility = View.INVISIBLE
        swipeRefresh.isRefreshing = false
    }

    override fun onFailure() {
        progressBar.visibility = View.INVISIBLE
        swipeRefresh.isRefreshing = false
        Toast.makeText(this.activity, "Нет доступа к сети", Toast.LENGTH_SHORT).show()

    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    companion object {
        @JvmStatic
        fun newInstance() = LecturesFragment()

        const val TAG = "LecturesFragment"
    }
}
