package ru.lab.fintechcourseproject.appearance.mainScreen.eventsTab

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.hannesdorfmann.mosby3.mvp.MvpFragment
import kotlinx.android.synthetic.main.card_archived_events.*
import kotlinx.android.synthetic.main.card_archived_events.view.*
import kotlinx.android.synthetic.main.card_relevant_events.*
import kotlinx.android.synthetic.main.card_relevant_events.view.*
import kotlinx.android.synthetic.main.fragment_events.*
import ru.lab.fintechcourseproject.ModelType
import ru.lab.fintechcourseproject.R
import ru.lab.fintechcourseproject.appearance.mainScreen.OnFragmentInteractionListener
import ru.lab.fintechcourseproject.database.events.DBEvent
import ru.lab.fintechcourseproject.getModel

class EventsFragment : SwipeRefreshLayout.OnRefreshListener, MvpFragment<IEventsView, EventsPresenter>(), IEventsView {

    override fun createPresenter(): EventsPresenter {
        return EventsPresenter(getModel(this.activity!!, ModelType.EVENTS_MODEL) as EventsModel)
    }

    private var listener: OnFragmentInteractionListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
//        if (context is OnFragmentInteractionListener) {
//            listener = context
//        } else {
//            throw RuntimeException("$context must implement OnFragmentInteractionListener")
//        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter.loadEvents()
        arguments?.let {
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_events, container, false)
        with(view) {
            with(rv_archived_events) {
                adapter = ArchivedEventsAdapter(ArrayList())
                layoutManager = LinearLayoutManager(activity)
            }
            with(rv_relevant_events) {
                adapter = RelevantEventsAdapter(ArrayList())
                layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
            }
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        swipe_refresh_events.setOnRefreshListener(this)
    }

    override fun onRefresh() {
        presenter.loadEvents(EventsPresenter.DataSource.UPDATE)
    }

    override fun onLoadingProgress() {
        swipe_refresh_events.isRefreshing = true
    }

    override fun onResult(active: List<DBEvent>, archive: List<DBEvent>) {
        with(rv_archived_events) {
            val adapter = this.adapter as ArchivedEventsAdapter
            adapter.events = archive
            adapter.notifyDataSetChanged()
        }
        with(rv_relevant_events) {
            val adapter = this.adapter as RelevantEventsAdapter
            adapter.events = active
            adapter.notifyDataSetChanged()
        }
        swipe_refresh_events.isRefreshing = false
    }

    override fun onFailure() {
        swipe_refresh_events.isRefreshing = false
        Toast.makeText(this.activity, "Ошибка подключения. Проверьте сеть.", Toast.LENGTH_LONG).show()

    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    companion object {
        const val TAG = "EventsFragment"
        @JvmStatic
        fun newInstance() = EventsFragment()
    }
}
