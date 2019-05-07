package ru.lab.fintechcourseproject.network.events

import com.google.gson.annotations.SerializedName
import ru.lab.fintechcourseproject.database.events.DBEvent

class EventsContainer(
    @SerializedName("active")
    val active: ArrayList<EventData>,
    @SerializedName("archive")
    val archive: ArrayList<EventData>
)


fun convertToDatabaseEvent(event: EventData, status: String): DBEvent {
    return DBEvent(
        event.title,
        event.type?.typeName,
        event.type?.color,
        event.date,
        status
    )
}
