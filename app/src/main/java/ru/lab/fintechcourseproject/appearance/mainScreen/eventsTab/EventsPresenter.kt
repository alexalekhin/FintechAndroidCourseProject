package ru.lab.fintechcourseproject.appearance.mainScreen.eventsTab

import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter

class EventsPresenter(private val model: EventsModel) : MvpBasePresenter<IEventsView>() {

    init {
        model.loadEvents(DataSource.UNSPECIFIED)
        model.eventsState.observeForever {
            ifViewAttached { view ->
                when (it) {
                    EventsModel.State.LOADED_FROM_NETWORK -> view.onResult(
                        model.activeEvents.value!!,
                        model.archivedEvents.value!!
                    )
                    EventsModel.State.LOADED_FROM_MEM -> view.onResult(
                        model.activeEvents.value!!,
                        model.archivedEvents.value!!
                    )
                    EventsModel.State.IN_PROCESS -> view.onLoadingProgress()
                    EventsModel.State.NOT_LOADED -> view.onFailure()
                    else -> TODO()
                }
            }
        }
    }


    fun loadEvents(source: DataSource = DataSource.UNSPECIFIED) {
        model.loadEvents(source)
    }

    enum class DataSource {
        UPDATE, RELOAD, UNSPECIFIED
    }
}