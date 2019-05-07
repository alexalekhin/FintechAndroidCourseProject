package ru.lab.fintechcourseproject.appearance.mainScreen.eventsTab

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.lab.fintechcourseproject.Model
import ru.lab.fintechcourseproject.database.AppDb
import ru.lab.fintechcourseproject.database.events.DBEvent
import ru.lab.fintechcourseproject.network.NetworkService
import ru.lab.fintechcourseproject.network.events.EventsContainer
import ru.lab.fintechcourseproject.network.events.convertToDatabaseEvent
import java.util.*
import javax.inject.Inject

class EventsModel @Inject constructor(
    private val fintechService: NetworkService,
    private val database: AppDb
) : Model {
    private var mutableArchivedEvents: MutableLiveData<List<DBEvent>> = MutableLiveData()
    private var mutableActiveEvents: MutableLiveData<List<DBEvent>> = MutableLiveData()
    private var mutableEventsState: MutableLiveData<State> = MutableLiveData()
    val archivedEvents: LiveData<List<DBEvent>> = mutableArchivedEvents
    val activeEvents: LiveData<List<DBEvent>> = mutableActiveEvents
    val eventsState: LiveData<State> = mutableEventsState


    fun loadEvents(source: EventsPresenter.DataSource = EventsPresenter.DataSource.UNSPECIFIED) {
        mutableEventsState.value = State.IN_PROCESS
        when (source) {
            EventsPresenter.DataSource.UNSPECIFIED -> {
                mutableArchivedEvents.value = database.getEventDao().getArchivedEvents()
                mutableActiveEvents.value = database.getEventDao().getActiveEvents()
                if (mutableArchivedEvents.value!!.isEmpty() || mutableActiveEvents.value!!.isEmpty()) {
                    updateEvents()
                }
            }
            EventsPresenter.DataSource.RELOAD -> {
                database.getEventDao().getActiveEvents()
                database.getEventDao().getArchivedEvents()
                mutableEventsState.value = State.LOADED_FROM_MEM
            }
            EventsPresenter.DataSource.UPDATE -> updateEvents()
        }
    }

    private fun updateEvents() {
        fintechService.getEvents().enqueue(
            object : Callback<EventsContainer> {
                override fun onResponse(call: Call<EventsContainer>, response: Response<EventsContainer>) {
                    val newArchivedEvents: ArrayList<DBEvent> = arrayListOf()
                    val newActiveEvents: ArrayList<DBEvent> = arrayListOf()

                    val eventsContainer = response.body()!!
                    for (event in eventsContainer.active) {
                        newActiveEvents.add(convertToDatabaseEvent(event, "active"))
                    }

                    for (event in eventsContainer.archive) {
                        newArchivedEvents.add(convertToDatabaseEvent(event, "archive"))
                    }
                    mutableActiveEvents.value = newActiveEvents
                    mutableArchivedEvents.value = newArchivedEvents
                    //todo: move to backing thread
                    newActiveEvents.forEach { database.getEventDao().insert(it) }
                    newArchivedEvents.forEach { database.getEventDao().insert(it) }
                    mutableEventsState.value = State.LOADED_FROM_NETWORK
                }

                override fun onFailure(call: Call<EventsContainer>, t: Throwable) {
                    mutableEventsState.value = State.NOT_LOADED
                }
            })
    }

    enum class State {
        NOT_LOADED, IN_PROCESS, LOADED_FROM_MEM, LOADED_FROM_NETWORK
    }
}
