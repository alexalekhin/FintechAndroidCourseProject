package ru.lab.fintechcourseproject.appearance.mainScreen.eventsTab

import com.hannesdorfmann.mosby3.mvp.MvpView
import ru.lab.fintechcourseproject.database.events.DBEvent

interface IEventsView: MvpView {
    fun onLoadingProgress()
    fun onResult(active: List<DBEvent>, archive: List<DBEvent>)
    fun onFailure()
}