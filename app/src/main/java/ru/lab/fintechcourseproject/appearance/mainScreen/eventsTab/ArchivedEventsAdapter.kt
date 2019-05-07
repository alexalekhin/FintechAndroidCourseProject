package ru.lab.fintechcourseproject.appearance.mainScreen.eventsTab

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_event_archive.view.*
import ru.lab.fintechcourseproject.R
import ru.lab.fintechcourseproject.database.events.DBEvent

/**
 * Адаптер к RecyclerView для отображения событий
 */
class ArchivedEventsAdapter(var events: List<DBEvent>) :
    RecyclerView.Adapter<ArchivedEventsAdapter.EventsViewHolder>() {
    override fun getItemCount() = events.size

    override fun onBindViewHolder(holder: EventsViewHolder, pos: Int) {
        val event = events[pos]
        with(holder) {
            eventTitle.text = event.title
            eventType.text = event.typeName
            when (event.color) {
                "purple" -> eventImage.setImageResource(R.mipmap.group_3)
                "red" -> eventImage.setImageResource(R.mipmap.group_6)
                "orange" -> eventImage.setImageResource(R.mipmap.group_8)
                "green" -> eventImage.setImageResource(R.mipmap.group_9)
                else -> eventImage.setImageResource(R.mipmap.group_4)
            }
        }
    }

    override fun onCreateViewHolder(holder: ViewGroup, p1: Int): EventsViewHolder {
        val view = LayoutInflater
            .from(holder.context)
            .inflate(R.layout.item_event_archive, holder, false)
        return EventsViewHolder(view)
    }

    class EventsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val eventTitle: TextView = itemView.tv_event_name
        val eventType: TextView = itemView.tv_event_type
        val eventImage: ImageView = itemView.iv_event
    }
}
