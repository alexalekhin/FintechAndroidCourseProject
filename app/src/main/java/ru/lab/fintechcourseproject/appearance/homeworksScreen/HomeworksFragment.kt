package ru.lab.fintechcourseproject.appearance.homeworksScreen

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.lab.fintechcourseproject.FintechApplication
import ru.lab.fintechcourseproject.R
import ru.lab.fintechcourseproject.appearance.lecturesScreen.OnLectureFragmentInteractionListener
import ru.lab.fintechcourseproject.database.homeworks.DBHomework

class HomeworksFragment : Fragment() {
    private var listener: OnLectureFragmentInteractionListener? = null
    private var hws: ArrayList<DBHomework>? = null

    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            val lecId: Long = it.getLong(LECTURE_ID_CODE)
            val database = (this.activity?.application as FintechApplication).appDb
            hws = ArrayList(database.getHomeworkDao().getHomeWorksByLectureId(lecId))
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_lecture_details, container, false)
        recyclerView = view.findViewById(R.id.rv_homeworks)
        recyclerView.layoutManager = LinearLayoutManager(this.activity)
        setupHomeworkAdapter()
        return view
    }

    private fun setupHomeworkAdapter() {
        val lecturesAdapter = hws?.let { HomeworkAdapter(it) }
        recyclerView.adapter = lecturesAdapter
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnLectureFragmentInteractionListener) {
            listener = context
        } else {
            throw IllegalStateException("$context must implement OnLectureFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }


    companion object {
        @JvmStatic
        fun newInstance(lecId: Long) =
            HomeworksFragment().apply {
                arguments = Bundle().apply {
                    putLong(LECTURE_ID_CODE, lecId)
                }
            }

        const val LECTURE_ID_CODE = "lecture_id"
        const val TAG = "HomeworksFragment"
    }
}
