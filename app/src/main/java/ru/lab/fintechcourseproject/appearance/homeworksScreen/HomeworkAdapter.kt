package ru.lab.fintechcourseproject.appearance.homeworksScreen

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import org.threeten.bp.Instant
import org.threeten.bp.LocalDateTime
import org.threeten.bp.ZoneId
import ru.lab.fintechcourseproject.R
import ru.lab.fintechcourseproject.database.homeworks.DBHomework

/**
 * Адаптер к RecyclerView для отображения домашек
 */
class HomeworkAdapter(private var hws: List<DBHomework>) :
    RecyclerView.Adapter<HomeworkAdapter.HomeworkViewHolder>() {
    override fun getItemCount() = hws.size

    override fun onBindViewHolder(holder: HomeworkViewHolder, pos: Int) {
        holder.apply {
            title.text = hws[pos].title
            status.text = "Статус: ${hws[pos].status}"
            mark.text = "Оценка: ${hws[pos].mark}/${hws[pos].markMax} "
            deadline.text = ""
            val instant: Instant
            val localDateTime: LocalDateTime
            hws[pos].deadline?.run {
                instant = Instant.parse(hws[pos].deadline)
                localDateTime = instant.atZone(ZoneId.systemDefault()).toLocalDateTime()
                deadline.text = "До: ${localDateTime.toLocalDate()} ${localDateTime.hour}:${localDateTime.minute}"
            }
        }
    }

    override fun onCreateViewHolder(holder: ViewGroup, p1: Int): HomeworkViewHolder {
        val view = LayoutInflater
            .from(holder.context)
            .inflate(R.layout.card_homework, holder, false)
        return HomeworkViewHolder(view)
    }

    class HomeworkViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.tv_hw_title)
        val status: TextView = itemView.findViewById(R.id.tv_status)
        val mark: TextView = itemView.findViewById(R.id.tv_mark)
        val deadline: TextView = itemView.findViewById(R.id.tv_deadline)
    }
}
