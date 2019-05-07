package ru.lab.fintechcourseproject.appearance.mainScreen.coursesTab.rating

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.lab.fintechcourseproject.Model
import ru.lab.fintechcourseproject.database.AppDb
import ru.lab.fintechcourseproject.database.homeworks.DBHomework
import ru.lab.fintechcourseproject.database.lectures.DBLecture
import ru.lab.fintechcourseproject.network.NetworkService
import ru.lab.fintechcourseproject.network.lectures.HomeworksContainer
import ru.lab.fintechcourseproject.network.lectures.convertLecture
import ru.lab.fintechcourseproject.network.lectures.getHomeworksOfLecture
import ru.lab.fintechcourseproject.network.students.StudentsContainer
import javax.inject.Inject
import kotlin.math.round

class RatingModel @Inject constructor(
    private val fintechService: NetworkService,
    private val database: AppDb,
    private val context: Context
) : Model {

    private lateinit var homeworkContainer: HomeworksContainer
    private lateinit var studentsContainer: StudentsContainer
    private var lectures: List<DBLecture> = arrayListOf()
    private var mutableRatingState: MutableLiveData<State> = MutableLiveData()
    private var mutableRating: MutableLiveData<RatingData> = MutableLiveData()
    val ratingState: LiveData<State> = mutableRatingState
    val rating: LiveData<RatingData> = mutableRating


    fun loadRating(source: RatingPresenter.DataSource = RatingPresenter.DataSource.UNSPECIFIED) {
        mutableRatingState.value = State.IN_PROCESS
        when (source) {
            RatingPresenter.DataSource.UPDATE -> updateHomeworkData()
            RatingPresenter.DataSource.RELOAD -> lectures = database.getLectureDao().getLectures()
            RatingPresenter.DataSource.UNSPECIFIED -> {
                updateHomeworkData()
            }
        }
    }

    private fun updateHomeworkData() {
        val cookie = getCookieFromMemory()
        val newLectures: ArrayList<DBLecture> = arrayListOf()
        fintechService.getHomeworks(cookie).enqueue(object : Callback<HomeworksContainer> {
            override fun onFailure(call: Call<HomeworksContainer>, t: Throwable) {
                mutableRatingState.value = State.NOT_LOADED
            }

            override fun onResponse(call: Call<HomeworksContainer>, response: Response<HomeworksContainer>) {
                if (response.isSuccessful) {
                    homeworkContainer = response.body()!!
                    val homeworks: ArrayList<DBHomework> = arrayListOf()
                    for (lecture in homeworkContainer.lectures) {
                        newLectures.add(convertLecture(lecture))
                        getHomeworksOfLecture(lecture).forEach { homeworks.add(it) }
                    }

                    lectures = newLectures.sortedBy { it.id }
                    updateRatingData()
                }
            }
        })
    }

    private fun updateRatingData() {
        val cookie = getCookieFromMemory()

        fintechService.getStudents(cookie).enqueue(
            object : Callback<java.util.ArrayList<StudentsContainer>> {
                override fun onResponse(
                    call: Call<java.util.ArrayList<StudentsContainer>>,
                    response: Response<java.util.ArrayList<StudentsContainer>>
                ) {
                    studentsContainer = response.body()!![1]
                    mutableRating.value = getRating()
                    mutableRatingState.value = State.LOADED_FROM_NETWORK
                }

                override fun onFailure(call: Call<java.util.ArrayList<StudentsContainer>>, t: Throwable) {
                    mutableRatingState.value = State.NOT_LOADED
                }
            })
    }

    fun getRating(): RatingData {
        var pointsSum = 0.0
        homeworkContainer.lectures.forEach { lecture -> for (task in lecture.tasks) pointsSum += task.mark.toDouble() }

        val pointsList: ArrayList<Double> = arrayListOf()
        studentsContainer.students.forEach { student -> pointsList.add(student.grades[student.grades.size - 1].mark.round(2)) }
        pointsList.sortDescending()

        var testsMade = 0
        var testsNum = 0
        var hwsMade = 0
        var hwsNum = 0
        homeworkContainer.lectures.forEach { lecture ->
            for (task in lecture.tasks) {
                if (task.task.taskType == "full") {
                    hwsNum++
                    if (task.status == "accepted") hwsMade++
                }
                if (task.task.taskType == "test_during_lecture") {
                    testsNum++
                    if (task.status == "accepted") testsMade++
                }
            }
        }

        return RatingData(
            pointsSum.toInt(),
            pointsList.indexOf(pointsSum.round(2)) + 1,
            pointsList.size,
            testsMade,
            testsNum,
            hwsMade,
            hwsNum,
            homeworkContainer.lectures.size
        )
    }

    private fun getCookieFromMemory(): String {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        return prefs.getString(COOKIE_NAME, "")!!
    }

    companion object {
        const val PREFS_NAME = "ru.lab.fintechcourseproject"
        const val COOKIE_NAME = "cookie"
    }

    enum class State {
        NOT_LOADED, IN_PROCESS, LOADED_FROM_MEM, LOADED_FROM_NETWORK
    }
}

fun Double.round(decimals: Int): Double {
    var multiplier = 1.0
    repeat(decimals) { multiplier *= 10 }
    return round(this * multiplier) / multiplier
}
