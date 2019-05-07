package ru.lab.fintechcourseproject.appearance.performanceScreen

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.lab.fintechcourseproject.R
import ru.lab.fintechcourseproject.database.students.DBStudent
import ru.lab.fintechcourseproject.misc.views.InitialsRoundView
import java.text.DecimalFormat
import kotlin.math.roundToInt


/**
 * Адаптер к RecyclerView для отображения студентов
 */

class StudentsAdapter(students: ArrayList<DBStudent>) :
    RecyclerView.Adapter<StudentsAdapter.StudentViewHolder>() {
    var students: ArrayList<DBStudent> = students
    set(newList) {
        field = ArrayList(newList)
        _students = ArrayList(newList)
    }
    private var _students: List<DBStudent> = students.toList()

    fun filter(string: String) {
        _students = if (string.isEmpty()) {
            ArrayList(students)
        } else {
            ArrayList(students.filter { student -> student.name.toLowerCase().contains(string.toLowerCase()) }).apply {
                sortWith(compareByDescending<DBStudent> { it.points }.thenBy { it.name })
            }
        }
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(
        studentLayoutHolder: StudentViewHolder,
        studentNum: Int
    ) {
        val student = _students[studentNum]
        studentLayoutHolder.textView.text = student.name
        studentLayoutHolder.initialsView.initials = findInitials(student.name)
        val format = DecimalFormat("#.##")
        val points = student.points
        val plural =
            studentLayoutHolder
                .pointsContact
                .context
                .resources
                .getQuantityString(
                    R.plurals.points,
                    points.roundToInt(),
                    format.format(points)
                )
        studentLayoutHolder.pointsContact.text = plural
    }

    override fun getItemCount() = _students.size

    private fun findInitials(text: String?): String? {
        if (text?.length!! <= 2) return text
        val split = text.split(" ".toRegex()).dropLastWhile { it.isEmpty() }
        var result = split[0][0].toString()
        if (split.size >= 2) result += split[1][0].toString()
        return result
    }


    fun sortElementsByName(isAscending: Boolean) {
        val studentsTmp = ArrayList(_students)
        if (isAscending) studentsTmp.sortBy { it.name } else studentsTmp.sortByDescending { it.name }
        _students = studentsTmp
        notifyDataSetChanged()
    }

    fun sortElementsByPoints(isAscending: Boolean) {
        val studentsTmp = ArrayList(_students)
        if (isAscending) studentsTmp.sortBy { it.points } else studentsTmp.sortByDescending { it.points }
        _students = studentsTmp
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(contactLayout: ViewGroup, viewType: Int): StudentViewHolder {
        val view = LayoutInflater.from(contactLayout.context).inflate(R.layout.item_list_student, contactLayout, false)
        return StudentViewHolder(view)
    }

    class StudentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textView: TextView = itemView.findViewById(R.id.tv_student_name)
        val initialsView: InitialsRoundView = itemView.findViewById(R.id.circleView)
        val pointsContact: TextView = itemView.findViewById(R.id.tv_points_of_student)
    }
}
