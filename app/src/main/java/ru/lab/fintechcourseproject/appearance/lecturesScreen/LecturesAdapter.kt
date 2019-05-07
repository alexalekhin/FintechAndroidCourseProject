package ru.lab.fintechcourseproject.appearance.lecturesScreen

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.lab.fintechcourseproject.R
import ru.lab.fintechcourseproject.database.lectures.DBLecture

/**
 * Адаптер к RecyclerView для отображения лекций
 */
class LecturesAdapter(var lectures: List<DBLecture>, private val listener: OnLectureFragmentInteractionListener?) :
    RecyclerView.Adapter<LecturesAdapter.LectureViewHolder>() {
    override fun getItemCount() = lectures.size

    override fun onBindViewHolder(holder: LectureViewHolder, pos: Int) {
        holder.apply {
            lectureTitle.text = lectures[pos].lecTitle
        }
        holder.card.setOnClickListener {
            listener?.showHomeworkDataByLectureId(lectures[pos].id)
        }
    }

    override fun onCreateViewHolder(holder: ViewGroup, p1: Int): LectureViewHolder {
        val view = LayoutInflater
            .from(holder.context)
            .inflate(R.layout.card_lecture, holder, false)


        return LectureViewHolder(view)
    }

    class LectureViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val lectureTitle: TextView = itemView.findViewById(R.id.tv_lecture_title)
        val card = itemView
    }
}
