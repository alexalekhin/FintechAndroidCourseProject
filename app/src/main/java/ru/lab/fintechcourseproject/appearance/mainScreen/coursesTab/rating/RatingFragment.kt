package ru.lab.fintechcourseproject.appearance.mainScreen.coursesTab.rating

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import com.hannesdorfmann.mosby3.mvp.MvpFragment
import kotlinx.android.synthetic.main.fragment_rating.*
import ru.lab.fintechcourseproject.ModelType
import ru.lab.fintechcourseproject.R
import ru.lab.fintechcourseproject.appearance.lecturesScreen.RatingDetailsActivity
import ru.lab.fintechcourseproject.getModel
import java.text.DecimalFormat

class RatingFragment : MvpFragment<IRatingView, RatingPresenter>(), IRatingView {
    override fun createPresenter(): RatingPresenter {
        return RatingPresenter(getModel(this.activity!!, ModelType.RATING_MODEL) as RatingModel)
    }

    override fun onResult(rating: RatingData) {
        tv_current_rating_value.text = "${rating.place}/${rating.studentsNum}"
        tv_tests_value.text = "${rating.testsMade}/${rating.testsNum}"
        tv_hw_value.text = "${rating.homeworksMade}/${rating.homeworksNum}"
        tv_lessons_num.text = "${rating.lecturesNum}"

        val format = DecimalFormat("#")
        val plural =
            context!!.resources.getQuantityString(R.plurals.points, rating.points, format.format(rating.points))
        tv_current_points.text = plural

        tv_current_points.visibility = View.VISIBLE
        tv_current_rating_value.visibility = View.VISIBLE
        tv_tests_value.visibility = View.VISIBLE
        tv_hw_value.visibility = View.VISIBLE
        tv_lessons_num.visibility = View.VISIBLE

        pb_rating.visibility = View.INVISIBLE
    }

    override fun onLoadingProgress() {
        tv_current_points.visibility = View.INVISIBLE
        tv_current_rating_value.visibility = View.INVISIBLE
        tv_tests_value.visibility = View.INVISIBLE
        tv_hw_value.visibility = View.INVISIBLE
        tv_lessons_num.visibility = View.INVISIBLE

        pb_rating.visibility = View.VISIBLE
    }

    override fun onFailure() {
        pb_rating.visibility = View.INVISIBLE
        tv_current_points.visibility = View.VISIBLE
        tv_current_rating_value.visibility = View.VISIBLE
        tv_tests_value.visibility = View.VISIBLE
        tv_hw_value.visibility = View.VISIBLE
        tv_lessons_num.visibility = View.VISIBLE

        pb_rating.visibility = View.INVISIBLE
        Toast.makeText(this.activity, "Ошибка подключения. Проверьте сеть.", Toast.LENGTH_LONG).show()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_rating, container, false)
        val detailsButton: LinearLayout = view.findViewById(R.id.layout_rating_header)
        detailsButton.setOnClickListener {
            val intent = Intent(activity, RatingDetailsActivity::class.java)
            startActivity(intent)
        }
        presenter.loadRating()
        return view
    }

    companion object {
        fun newInstance() = RatingFragment()
        const val TAG = "RatingFragment"
    }
}
