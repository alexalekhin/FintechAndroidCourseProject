package ru.lab.fintechcourseproject.appearance.lecturesScreen

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.lab.fintechcourseproject.R
import ru.lab.fintechcourseproject.appearance.homeworksScreen.HomeworksFragment


class RatingDetailsActivity : AppCompatActivity(), OnLectureFragmentInteractionListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rating_details)

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.lec_container, LecturesFragment.newInstance(), LecturesFragment.TAG)
            .commit()
    }

    override fun showHomeworkDataByLectureId(lecId: Long) {
        supportFragmentManager
            .beginTransaction()
            .addToBackStack(HomeworksFragment.TAG)
            .replace(R.id.lec_container, HomeworksFragment.newInstance(lecId), HomeworksFragment.TAG)
            .commit()
    }
}
