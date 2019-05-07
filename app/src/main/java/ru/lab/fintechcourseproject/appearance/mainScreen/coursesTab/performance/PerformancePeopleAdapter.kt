package ru.lab.fintechcourseproject.appearance.mainScreen.coursesTab.performance

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.image_with_badge_item.view.*
import ru.lab.fintechcourseproject.R
import ru.lab.fintechcourseproject.misc.views.BadgeView
import ru.lab.fintechcourseproject.misc.views.InitialsRoundView


/**
 * Адаптер к RecyclerView для отображения людей в разделе успеваемость
 */
class PerformancePeopleAdapter(var people: ArrayList<PerformancePerson>) :
    RecyclerView.Adapter<PerformancePeopleAdapter.PersonsViewHolder>() {
    override fun getItemCount() = people.size

    override fun onBindViewHolder(holder: PersonsViewHolder, pos: Int) {
        val person = people[pos]
        with(holder) {
            name.text = findName(person.name)
            initialsView.initials = findInitials(person.name)
            badgeView.text = person.points.toString()
            badgeView.visibility = if (badgeView.text == "0") {
                View.INVISIBLE
            } else {
                View.VISIBLE
            }
        }
    }

    private fun findName(text: String): String {
        val split = text.split(" ".toRegex()).dropLastWhile { it.isEmpty() }
        return split[1]
    }

    private fun findInitials(text: String?): String? {
        if (text?.length!! <= 2) return text
        val split = text.split(" ".toRegex()).dropLastWhile { it.isEmpty() }
        var result = split[0][0].toString()
        if (split.size >= 2) result += split[1][0].toString()
        return result
    }

    override fun onCreateViewHolder(holder: ViewGroup, p1: Int): PersonsViewHolder {
        val view = LayoutInflater
            .from(holder.context)
            .inflate(R.layout.image_with_badge_item, holder, false)
        return PersonsViewHolder(view)
    }

    class PersonsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val initialsView: InitialsRoundView = itemView.round_initials
        val badgeView: BadgeView = itemView.badge
        val name: TextView = itemView.tv_name
    }
}
