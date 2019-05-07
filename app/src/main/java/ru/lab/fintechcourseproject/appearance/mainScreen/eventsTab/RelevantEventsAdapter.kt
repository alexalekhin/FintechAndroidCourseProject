package ru.lab.fintechcourseproject.appearance.mainScreen.eventsTab

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_event_active.view.*
import org.threeten.bp.Instant
import org.threeten.bp.LocalDateTime
import org.threeten.bp.ZoneId
import ru.lab.fintechcourseproject.R
import ru.lab.fintechcourseproject.database.events.DBEvent


/**
 * Адаптер к RecyclerView для отображения событий
 */
class RelevantEventsAdapter(var events: List<DBEvent>) :
    RecyclerView.Adapter<RelevantEventsAdapter.EventsViewHolder>() {
    override fun getItemCount() = events.size

    override fun onBindViewHolder(holder: EventsViewHolder, pos: Int) {
        val event = events[pos]
        with(holder) {
            eventTitle.text = event.title
            val instant: Instant = Instant.parse(event.date)
            val localDateTime: LocalDateTime = instant.atZone(ZoneId.systemDefault()).toLocalDateTime()
            eventDate.text = "${localDateTime.dayOfMonth} ${localDateTime.month} ${localDateTime.year}"
            if (event.typeName != null) eventType.text = event.typeName else eventType.visibility = View.INVISIBLE
            when(event.color) {
                "purple" -> eventType.background = holder.itemView.context.getDrawable(R.drawable.rect_rounded_purple)
                "orange" -> eventType.background = holder.itemView.context.getDrawable(R.drawable.rect_rounded_red)
                "green" -> eventType.background = holder.itemView.context.getDrawable(R.drawable.rect_rounded_green)
            }
        }
    }

    override fun onCreateViewHolder(holder: ViewGroup, p1: Int): EventsViewHolder {
        val view = LayoutInflater
            .from(holder.context)
            .inflate(R.layout.item_event_active, holder, false)
        return EventsViewHolder(view)
    }

    class EventsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val eventTitle: TextView = itemView.tv_event_name
        val eventType: TextView = itemView.tv_event_type
        val eventDate: TextView = itemView.tv_event_date
        val eventImage: ImageView = itemView.iv_event
    }
}
